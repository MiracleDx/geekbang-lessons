package org.dongx.configuration.microprofile.config.servlet.listener;

import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.spi.ConfigProviderResolver;

import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;

/**
 * TODO
 *
 * @author <a href="mailto:dongxiang886@gmail.com>Dongx</a>
 * @since
 */
public class ConfigServletRequestListener implements ServletRequestListener {
	
	private static final ThreadLocal<Config> configThreadLocal = new ThreadLocal<>();
	
	@Override
	public void requestDestroyed(ServletRequestEvent sre) {
		ServletRequest request = sre.getServletRequest();
		ServletContext servletContext = request.getServletContext();
		ClassLoader classLoader = servletContext.getClassLoader();
		ConfigProviderResolver configProviderResolver = ConfigProviderResolver.instance();
		Config config = configProviderResolver.getConfig();
		configThreadLocal.set(config);
	}

	@Override
	public void requestInitialized(ServletRequestEvent sre) {
		// 防止 OOM
		configThreadLocal.remove();
	}
}
