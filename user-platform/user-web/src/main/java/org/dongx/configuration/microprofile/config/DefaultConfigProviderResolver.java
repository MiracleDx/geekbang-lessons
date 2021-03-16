package org.dongx.configuration.microprofile.config;

import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.spi.ConfigBuilder;
import org.eclipse.microprofile.config.spi.ConfigProviderResolver;

import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * TODO
 *
 * @author <a href="mailto:dongxiang886@gmail.com>Dongx</a>
 * @since
 */
public class DefaultConfigProviderResolver extends ConfigProviderResolver {
	
	@Override
	public Config getConfig() {
		return getConfig(null);
	}

	@Override
	public Config getConfig(ClassLoader loader) {
		ClassLoader classLoader = loader;
		if (classLoader == null) {
			classLoader = Thread.currentThread().getContextClassLoader();
		}
		ServiceLoader<Config> serviceLoader = ServiceLoader.load(Config.class, classLoader);
		Iterator<Config> iterator = serviceLoader.iterator();
		if (iterator.hasNext()) {
			// 获取 config SPI 第一个实现
			return iterator.next();
		}
		throw new IllegalStateException("No config implementation found");
	}

	@Override
	public ConfigBuilder getBuilder() {
		return null;
	}

	@Override
	public void registerConfig(Config config, ClassLoader classLoader) {

	}

	@Override
	public void releaseConfig(Config config) {

	}
}
