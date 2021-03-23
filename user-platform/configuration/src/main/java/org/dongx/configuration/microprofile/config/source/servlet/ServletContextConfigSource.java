package org.dongx.configuration.microprofile.config.source.servlet;

import org.dongx.configuration.microprofile.config.source.MapBasedConfigSource;

import javax.servlet.ServletContext;
import java.util.Enumeration;
import java.util.Map;

/**
 * TODO
 *
 * @author <a href="mailto:dongxiang886@gmail.com>Dongx</a>
 * @since
 */
public class ServletContextConfigSource extends MapBasedConfigSource {

	private Map configData;
	
	private final ServletContext servletContext;
	
	public ServletContextConfigSource(ServletContext servletContext) {
		super("ServletContext Init Parameters", 500);
		this.servletContext = servletContext;
		prepareConfigData();
	}
	
	@Override
	protected void prepareConfigData(Map configData) throws Throwable {
		// 将 this.configData 赋值为父类传递过来的空 Map
		this.configData = configData;
	}
	
	private void prepareConfigData() {
		Enumeration<String> parameterNames = servletContext.getInitParameterNames();
		while (parameterNames.hasMoreElements()) {
			String parameterName = parameterNames.nextElement();
			configData.put(parameterName, servletContext.getInitParameter(parameterName));
		}
	}
}
