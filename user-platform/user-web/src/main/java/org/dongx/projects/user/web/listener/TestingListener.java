package org.dongx.projects.user.web.listener;

import org.dongx.context.ComponentContext;
import org.dongx.projects.user.domain.User;
import org.dongx.projects.user.sql.DBConnectionManager;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
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
		
		testUser(dbConnectionManager.getEntityManager());

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

	@Override
	public void contextDestroyed(ServletContextEvent sce) {

	}
}
