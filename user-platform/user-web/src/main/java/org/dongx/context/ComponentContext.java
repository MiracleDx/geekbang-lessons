package org.dongx.context;

import org.dongx.function.ThrowableAction;
import org.dongx.function.ThrowableFunction;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.naming.*;
import javax.servlet.ServletContext;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Stream;

/**
 * 组件上下文（web 全局使用）
 *
 * @author <a href="mailto:dongxiang886@gmail.com>Dongx</a>
 * @since
 */
public class ComponentContext {

	/**
	 * 存储在 Servlet 上下文中的组件上下文名称
	 */
	public static final String CONTEXT_NAME = ComponentContext.class.getName();

	private static final Logger logger = Logger.getLogger(CONTEXT_NAME);

	/**
	 * JNDI 组件依赖查找的名称前缀
	 */
	private static final String COMPONENT_ENV_CONTEXT_NAME = "java:comp/env";

	/**
	 * Servlet 上下文
	 */
	private static ServletContext servletContext;

	// 设置上下文的常用方式
	//private static ApplicationContext applicationContext;
	//
	//public void setApplicationContext(ApplicationContext applicationContext) {
	//	ComponentContext.applicationContext = applicationContext;
	//	WebApplicationContextUtils.getRootWebApplicationContext()
	//}

	/**
	 * JNDI 上下文
	 */
	private Context envContext;

	/**
	 * 
	 */
	private ClassLoader classLoader;

	/**
	 * 组件容器
	 */
	private Map<String, Object> componentsMap = new LinkedHashMap<>();

	/**
	 * 获取 ComponentContext
	 * @return
	 */
	public static ComponentContext getInstance() {
		return (ComponentContext) servletContext.getAttribute(CONTEXT_NAME);
	}

	/**
	 * 初始化组件上下文
	 * @param servletContext 
	 */
	public void init(ServletContext servletContext) {
		ComponentContext.servletContext = servletContext;
		// 把当前组件上下文放入 Servlet 上下文
		servletContext.setAttribute(CONTEXT_NAME, this);
		// 获取 当前 ServletContext 的 ClassLoader（WebApp）
		this.classLoader = servletContext.getClassLoader();
		// 初始化 JNDI 上下文
		initEnvContext();
		// 实例化组件
		instantiateComponents();
		// 初始化组件
		initializeComponents();
	}

	/**
	 * 实例化组件
	 */
	protected void instantiateComponents() {
		// 遍历获取所有的组件名称
		List<String> componentNames = listAllComponentNames();
		// 通过依赖查找 实例化对象（Tomcat BeanFactory setter 方法的执行，仅支持简单类型）
		componentNames.forEach(name -> componentsMap.put(name, lookupComponent(name)));
	}


	/**
	 * 初始化组件（支持 Java 标准 Commons Annotation 生命周期）
	 * <ol>
	 *     <li>注入阶段 - {@link javax.annotation.Resource}</li>
	 *     <li>初始阶段 - {@link javax.annotation.PostConstruct}</li>
	 *     <li>销毁阶段 - {@link javax.annotation.PreDestroy}</li>
	 * </ol>
	 */
	protected void initializeComponents() {
		componentsMap.values().forEach(component -> {
			Class<?> componentClass = component.getClass();
			// 注入阶段 - {@link Resource}
			injectComponents(component, componentClass);
			// 初始阶段 - {@link PostConstruct}
			processPostConstruct(component, componentClass);
			// todo 销毁阶段 - {@link PreDestroy}
			processPreDestroy();
		});
	}

	/**
	 * 注入组件
	 * @param component 组件对象 
	 * @param componentClass 组件 Class
	 */
	private void injectComponents(Object component, Class<?> componentClass) {
		Stream.of(componentClass.getDeclaredFields())
				.filter(field -> {
					// 返回的 int 表示类、成员变量、方法的修饰符
					int mods = field.getModifiers();
					// 返回不是静态字段且包含 @Resource 注解的字段
					return !Modifier.isStatic(mods) && field.isAnnotationPresent(Resource.class);
				}).forEach(field -> {
					Resource resource  = field.getAnnotation(Resource.class);
					String resourceName = resource.name();
					// 对该字段进行依赖查找
					Object injectObject = lookupComponent(resourceName);
					field.setAccessible(true);
					// 注入目标对象
					try {
						field.set(component, injectObject);
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}
				});
	}

	/**
	 * 初始化组件
	 * @param component 组件对象 
	 * @param componentClass 组件 Class
	 */
	private void processPostConstruct(Object component, Class<?> componentClass) {
		Stream.of(componentClass.getMethods())
				.filter(method -> 
					// 非 static 方法
					!Modifier.isStatic(method.getModifiers())
							// 没有参数
							&& method.getParameterCount() == 0
							// 标注 @PostConstruct 注解
							&& method.isAnnotationPresent(PostConstruct.class)
				).forEach(method -> {
					try {
						// 执行目标方法
						method.invoke(component);
					} catch (Exception e) {
						throw new RuntimeException(e);
					}
				});
	}

	/**
	 * 销毁组件
	 */
	private void processPreDestroy() {
	}

	/**
	 * 列出所有的组件名称
	 * @return
	 */
	private List<String> listAllComponentNames() {
		// 根目录
		return listComponentNames("/");
	}

	/**
	 * 列出指定路径下的所有组件名称
	 * @param name 指定路径
	 * @return
	 */
	protected List<String> listComponentNames(String name) {
		return executeInContext(context -> {
			// 获取 JNDI 中组件的名称与 Class 的键值对
			NamingEnumeration<NameClassPair> e = executeInContext(context, ctx -> ctx.list(name), true);
			
			// 目录 - Context
			// 节点 -
			
			// 当前 JNDI 中没有子节点
			if (e == null) {
				return Collections.emptyList();
			}
			
			List<String> fullNames = new LinkedList<>();
			while (e.hasMoreElements()) {
				NameClassPair element = e.nextElement();
				String className = element.getClassName();
				Class<?> targetClass = classLoader.loadClass(className);
				// 如果当前名称是 Context 实现类，递归查找
				if (Context.class.isAssignableFrom(targetClass)) {
					fullNames.addAll(listComponentNames(element.getName()));
				} else {
					// 否则 当前名称绑定目标类型的话 添加该名称到集合中
					String fullName = name.startsWith("/") ? element.getName() : name + "/" + element.getName();
					fullNames.add(fullName);
				}
			}
			return fullNames;
		});
	}

	/**
	 * 在 Context 中执行，通过指定 ThrowableFunction 返回计算结果
	 * @param function
	 * @param <R>
	 * @return
	 */
	protected <R> R executeInContext(ThrowableFunction<Context, R> function) {
		return executeInContext(function, false);
	}

	/**
	 * 在 Context 中执行，通过指定 ThrowableFunction 返回计算结果
	 * @param function ThrowableFunction
	 * @param ignoredException 是否忽略异常
	 * @param <R> 返回结果类型
	 * @return 返回
	 * @see ThrowableFunction#apply(Object)
	 */
	protected <R> R executeInContext(ThrowableFunction<Context, R> function, boolean ignoredException) {
		return executeInContext(this.envContext, function, ignoredException);
	}

	private <R> R executeInContext(Context context, ThrowableFunction<Context,R> function, boolean ignoredException) {
		R result = null;
		try {
			result = ThrowableFunction.execute(context, function);
		} catch (Throwable e) {
			if (ignoredException) {
				logger.warning(e.getMessage());
			} else {
				throw new RuntimeException(e);
			}
		}
		return result;
	}

	/**
	 * 通过名称进行依赖查找
	 *
	 * @param name 组件名称
	 * @param <C> 组件
	 * @return
	 */
	public <C> C getComponent(String name) {
		return (C) componentsMap.get(name);
	}

	/**
	 * 获取所有的组件名称
	 *
	 * @return
	 */
	public List<String> getComponentNames() {
		return new ArrayList<>(componentsMap.keySet());
	}


	/**
	 * 在 JNDI 中进行依赖查找
	 * @param name 组件名称
	 * @param <C> 组件
	 * @return
	 */
	protected <C> C lookupComponent(String name) {
		return executeInContext(context -> (C) context.lookup(name));
	}
	
	/**
	 * 初始化 JNDI 上下文
	 */
	private void initEnvContext() {
		if (this.envContext != null) {
			return;
		}
		
		Context context = null;
		try {
			context = new InitialContext();
			this.envContext = (Context) context.lookup(COMPONENT_ENV_CONTEXT_NAME);
		} catch (NamingException e) {
			throw new RuntimeException(e);
		} finally {
			close(context);
		}
	}

	/**
	 * 关闭 JNDI 上下文
	 * @param context JNDI 上下文
	 */
	private static void close(Context context) {
		if (context != null) {
			ThrowableAction.execute(context::close);
		}
	}

}
