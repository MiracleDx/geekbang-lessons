package org.dongx.projects.user.web.listener;

import org.dongx.context.ComponentContext;
import org.dongx.projects.user.domain.User;
import org.dongx.projects.user.sql.DBConnectionManager;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.logging.Logger;

/**
 * 测试用途
 *
 * @author <a href="mailto:dongxiang886@gmail.com>Dongx</a>
 * @since
 */
@Deprecated
public class TestingListener implements ServletContextListener {

	private Logger logger = Logger.getLogger(this.getClass().getName());
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		ComponentContext context = ComponentContext.getInstance();
		DBConnectionManager dbConnectionManager = context.getComponent("bean/DBConnectionManager");
		dbConnectionManager.getConnection();

		testPropertyFromServletContext(sce.getServletContext());
		testPropertyFromJNDI(context);
		
		//testUser(dbConnectionManager.getEntityManager());

		// ConfigProviderResolver -> Config -> ConfigSource
		//ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		//ServiceLoader<ConfigProviderResolver> serviceLoader = ServiceLoader.load(ConfigProviderResolver.class, classLoader);
		//Iterator<ConfigProviderResolver> iterator = serviceLoader.iterator();
		//try {
		//	while (iterator.hasNext()) {
		//		ConfigProviderResolver providerResolver = iterator.next();
		//		Config config = providerResolver.getConfig(classLoader);
		//		
		//		String applicationName = config.getValue("application.name", String.class);
		//		System.out.println("the application name is " + applicationName);
		//		String javaHome = config.getValue("JAVA_HOME", String.class);
		//		System.out.println("the JAVA_HOME is " + javaHome);
		//		String logLevel = config.getValue("org.geektimes.level", String.class);
		//		System.out.println("the application log level is " + logLevel);
		//		Integer count = config.getValue("java.util.logging.FileHandler.count", Integer.class);
		//		System.out.println("the fileHandler count is " + count);
		//	}
		//} catch (Exception e) {
		//	e.printStackTrace();
		//}
		

		//DefaultConfigProviderResolver configProviderResolver = new DefaultConfigProviderResolver();
		//Config org.dongx.configuration.microprofile.config = configProviderResolver.getConfig(classLoader);
		//ConfigValue configValue = org.dongx.configuration.microprofile.config.getConfigValue("application.name");
		//System.out.println(configValue.getValue());

		logger.info("所有的 JNDI 组件名称：[");
		context.getComponentNames().forEach(logger::info);
		logger.info("]");
	}

	private void testUser(EntityManager entityManager) {
		User user = new User();
		user.setName("小马哥");
		user.setPassword("******");
		user.setEmail("mercyblitz@gmail.com");
		user.setPhoneNumber("abcdefg");
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		entityManager.persist(user);
		transaction.commit();
		System.out.println(entityManager.find(User.class, user.getId()));
	}

	private void testPropertyFromServletContext(ServletContext servletContext) {
		String propertyName = "application.name";
		logger.info("ServletContext Property[" + propertyName + "] : "
				+ servletContext.getInitParameter(propertyName));
	}

	private void testPropertyFromJNDI(ComponentContext context) {
		String propertyName = "maxValue";
		logger.info("JNDI Property[" + propertyName + "] : "
				+ context.lookupComponent(propertyName));
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {

	}
}
