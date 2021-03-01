package org.dongx.projects.user.web.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * 数据库连接初始化监听器
 *
 * @author <a href="mailto:dongxiang886@gmail.com>Dongx</a>
 * @since
 */
@WebListener
public class DBConnectionInitializerListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {

	}
}
