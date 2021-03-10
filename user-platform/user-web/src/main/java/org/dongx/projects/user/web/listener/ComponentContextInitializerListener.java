package org.dongx.projects.user.web.listener;

import org.dongx.context.ComponentContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * {@link org.dongx.context.ComponentContext} 初始化器
 *
 * @author <a href="mailto:dongxiang886@gmail.com>Dongx</a>
 * @since
 */
@WebListener
public class ComponentContextInitializerListener implements ServletContextListener {
	
	private ServletContext servletContext;

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		// 获取 Servlet 上下文
		this.servletContext = sce.getServletContext();
		// 初始化组件上下文
		ComponentContext context = new ComponentContext();
		context.init(servletContext);
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {

	}
}
