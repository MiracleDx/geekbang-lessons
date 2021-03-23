package org.dongx.web.mvc;

import org.apache.commons.lang.StringUtils;
import org.dongx.context.ComponentContext;
import org.dongx.web.mvc.controller.Controller;
import org.dongx.web.mvc.controller.PageController;
import org.dongx.web.mvc.controller.RestController;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.HttpMethod;
import javax.ws.rs.Path;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Servlet 请求分发器
 * {@link DispatcherServlet}
 *
 * @author <a href="mailto:dongxiang886@gmail.com>Dongx</a>
 * @since 1.0
 */
public class FrontControllerServlet extends HttpServlet {

	/**
	 * 请求路径和 {@link HandlerMethodInfo} 映射关系缓存
	 */
	private Map<String, HandlerMethodInfo> handlerMethodInfoMapping = new HashMap<>();

	@Override
	public void init(ServletConfig servletConfig) {
		try {
			initHandleMethod();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 利用 SPI 读取所有的 Controller 的注解信息 @Path
	 */
	private void initHandleMethod() {
		for (Controller controller : ServiceLoader.load(Controller.class)) {
			Class<? extends Controller> controllerClass = controller.getClass();
			Path pathFromClass = controllerClass.getAnnotation(Path.class);
			String requestPath = "";
			// Controller 上标注 url 时 request = path#value()
			if (pathFromClass != null) {
				requestPath = pathFromClass.value();
			}
			
			Method[] publicMethods = controllerClass.getMethods();
			// 处理支持的 HTTP 集合
			for (Method method : publicMethods) {
				// 每个方法需要重新拼接 requestPath
				String realRequestPath = requestPath;
				Set<String> supportedMethods = findSupportedHttpMethods(method);
				Path pathFromMethod = method.getAnnotation(Path.class);
				if (pathFromMethod != null) {
					realRequestPath += pathFromMethod.value();
				}

				// 没有 requestPath 不存入映射关系
				if ("".equals(realRequestPath)) {
					continue;
				}
				
				// 只有 method 包含 HttpMethod 注解才可以进行映射
				if (!supportedMethods.isEmpty()) {
					// url 映射相同需要抛出异常
					if (handlerMethodInfoMapping.get(realRequestPath) != null) {
						throw new IllegalStateException("Ambiguous mapping. Cannot map " + realRequestPath + ", This is already " + controller + " exists");
					}
					handlerMethodInfoMapping.put(realRequestPath, new HandlerMethodInfo(realRequestPath, controller, method, supportedMethods));
				}
			}
		}
	}

	/**
	 * 获取处理方法中标注的 HTTP 方法集合
	 *
	 * @param method 处理方法
	 * @return
	 */
	private Set<String> findSupportedHttpMethods(Method method) {
		Set<String> supportedHttpMethods = new LinkedHashSet<>();
		for (Annotation annotationFromMethod : method.getAnnotations()) {
			HttpMethod httpMethod = annotationFromMethod.annotationType().getAnnotation(HttpMethod.class);
			if (httpMethod != null) {
				supportedHttpMethods.add(httpMethod.value());
			}
		}

		//if (supportedHttpMethods.isEmpty()) {
		//	supportedHttpMethods.addAll(Arrays.asList(HttpMethod.GET, HttpMethod.POST,
		//			HttpMethod.PUT, HttpMethod.DELETE, HttpMethod.HEAD, HttpMethod.OPTIONS));
		//}
		return supportedHttpMethods;
	}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 建立映射关系 requestURI = /a/hello/world
		String requestURI = request.getRequestURI();
		// contextPath = /a or "/" or ""
		String servletContextPath = request.getContextPath();
		String prefixPath = servletContextPath;
		// 映射路径（子路径） 去除 contextPath 路径
		String requestMappingPath = StringUtils.substringAfter(requestURI, StringUtils.replace(prefixPath, "//", "/"));
		// 获取方法元信息
		HandlerMethodInfo handlerMethodInfo = handlerMethodInfoMapping.get(requestMappingPath);
		if (handlerMethodInfo != null) {
			try {
				String httpMethod = request.getMethod();
				if (!handlerMethodInfo.getSupportedHttpMethods().contains(httpMethod)) {
					// HTTP 方法不支持
					response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
					response.getWriter().write("The method not allowed " + httpMethod);
					return;
				}

				Controller controller = handlerMethodInfo.getHandlerController();
				if (controller instanceof PageController) {
					PageController pageController = PageController.class.cast(controller);
					// todo 魔法值可优化
					Object injectionController = ComponentContext.getInstance().getComponent("bean/" + pageController.getClass().getSimpleName());
					if (injectionController != null) {
						controller = (Controller) injectionController;
					}
					Object result = handlerMethodInfo.getHandlerMethod().invoke(controller, request, response);
					
					// 获取请求上下文
					ServletContext servletContext = request.getServletContext();
					String viewPath = "";
					if (result instanceof String) {
						viewPath = result.toString();
						// 页面请求 forward
						// request -> RequestDispatcher forward
						// RequestDispatcher requestDispatcher = request.getRequestDispatcher(viewPath); 支持相对路径和绝对路径

						// ServletContext -> RequestDispatcher forward
						// ServletContext -> RequestDispatcher 必须以 "/" 开头 只能使用绝对路径
						if (!viewPath.startsWith("/")) {
							viewPath = "/" + viewPath;
						}
					}
					
					// 请求转发
					RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher(viewPath);
					requestDispatcher.forward(request, response);
					return;
				} else if (controller instanceof RestController) {
					// TODO
				}
			} catch (Throwable throwable) {
				if (throwable.getCause() instanceof IOException) {
					throw (IOException) throwable.getCause();
				} else {
					throw new ServletException(throwable.getCause());
				}
			}
		} else {
			//会抛出 Not found /favicon.ico 需要处理
			if (!requestMappingPath.contains("/favicon.ico")) {
				response.setStatus(HttpServletResponse.SC_NOT_FOUND);
				response.getWriter().write("Not found " + requestMappingPath);
				return;
			}
		}
	}
}
