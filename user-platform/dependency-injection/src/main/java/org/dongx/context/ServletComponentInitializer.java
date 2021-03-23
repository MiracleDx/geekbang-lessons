package org.dongx.context;

import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.util.Set;

/**
 * TODO
 *
 * @author <a href="mailto:dongxiang886@gmail.com>Dongx</a>
 * @since
 */
public class ServletComponentInitializer implements ServletContainerInitializer {
	
	@Override
	public void onStartup(Set<Class<?>> c, ServletContext servletContext) throws ServletException {
		// 初始化组件上下文
		ComponentContext context = new ComponentContext();
		context.init(servletContext);
	}
}
