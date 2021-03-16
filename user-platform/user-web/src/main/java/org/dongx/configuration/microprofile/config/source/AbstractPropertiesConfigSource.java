package org.dongx.configuration.microprofile.config.source;

import org.eclipse.microprofile.config.spi.ConfigSource;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 抽象属性配置类
 *
 * @author <a href="mailto:dongxiang886@gmail.com>Dongx</a>
 * @since
 */
abstract class AbstractPropertiesConfigSource implements ConfigSource {

	/**
	 * 配置属性
	 */
	private final Map<String, String> properties;

	AbstractPropertiesConfigSource() {
		Map<String, String> envProperties = initialProperties();
		this.properties = new ConcurrentHashMap<>(envProperties);
	}

	/**
	 * 初始化配置属性
	 * @return 
	 */
	abstract public Map initialProperties();
	
	
	@Override
	public Set<String> getPropertyNames() {
		return properties.keySet();
	}

	@Override
	public String getValue(String propertyName) {
		return properties.get(propertyName);
	}

	@Override
	public String getName() {
		throw new UnsupportedOperationException("please set properties source name.");
	}
}
