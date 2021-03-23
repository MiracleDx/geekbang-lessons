package org.dongx.configuration.microprofile.config.source.servlet;

import org.dongx.configuration.microprofile.config.ServletConfigContextHolder;
import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.spi.ConfigBuilder;
import org.eclipse.microprofile.config.spi.ConfigProviderResolver;

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
public class ServletConfigInitializer implements ServletContainerInitializer {
	
	@Override
	public void onStartup(Set<Class<?>> c, ServletContext servletContext) throws ServletException {
		// 增加 ServletContextListener
		//servletContext.addListener(ServletContextConfigInitializer.class);
		ServletContextConfigSource servletContextConfigSource = new ServletContextConfigSource(servletContext);
		// 获取当前 ClassLoader
		ClassLoader classLoader = servletContext.getClassLoader();
		ConfigProviderResolver configProviderResolver = ConfigProviderResolver.instance();
		ConfigBuilder configBuilder = configProviderResolver.getBuilder();
		// 配置 ClassLoader
		configBuilder.forClassLoader(classLoader);
		// 默认配置源（内建的，静态的）
		configBuilder.addDefaultSources();
		// 通过发现配置源（动态的）
		configBuilder.addDiscoveredConverters();
		// 增加扩展配置源（基于 Servlet 引擎）
		configBuilder.withSources(servletContextConfigSource);
		// 获取 Config
		Config config = configBuilder.build();
		// 注册 Config 关联到当前 ClassLoader
		configProviderResolver.registerConfig(config, classLoader);

		// 初始化全局配置上下文
		ServletConfigContextHolder configContextHolder = new ServletConfigContextHolder();
		configContextHolder.init(servletContext, config);

		//// 注册过滤器
		//servletContext.addFilter("ThreadLocalConfigFilter", ThreadLocalConfigFilter.class);
	}
}
