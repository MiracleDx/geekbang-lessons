package org.dongx.configuration.microprofile.config;

import org.eclipse.microprofile.config.Config;

import javax.servlet.ServletContext;

/**
 * TODO
 *
 * @author <a href="mailto:dongxiang886@gmail.com>Dongx</a>
 * @since
 */
public class ServletConfigContextHolder {

	public static final String CONFIG_SOURCE = "config_source";
	
	private static ServletContext servletContext;
	
	private Config config;
	
	public void init(ServletContext servletContext, Config config) {
		ServletConfigContextHolder.servletContext = servletContext;
		this.config = config;
		servletContext.setAttribute(CONFIG_SOURCE, this);
	}	

	public static ServletConfigContextHolder getInstance() {
		return (ServletConfigContextHolder) servletContext.getAttribute(CONFIG_SOURCE);
	}

	public Config getConfig() {
		return config;
	}
}
