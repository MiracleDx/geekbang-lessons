package org.dongx.configuration.microprofile.config.source;

import org.eclipse.microprofile.config.spi.ConfigSource;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 基于 Map 数据结构 {@link ConfigSource} 实现
 *
 * @author <a href="mailto:dongxiang886@gmail.com>Dongx</a>
 * @since
 */
public abstract class MapBasedConfigSource implements ConfigSource {
	
	private final String name;
	
	private final int ordinal;
	
	private final Map<String, String> source;
	
	protected MapBasedConfigSource(String name, int ordinal) {
		this.name = name;
		this.ordinal = ordinal;
		this.source = getProperties();
	}

	/**
	 * 获取配置数据 Map
	 * @return 不可变 Map 类型的配置数据
	 */
	@Override
	public final Map<String, String> getProperties() {
		Map<String, String> configData = new HashMap<>();
		try {
			prepareConfigData(configData);
		} catch (Throwable e) {
			throw new IllegalStateException("准备配置数据发生错误", e);
		}
		return Collections.unmodifiableMap(configData);
	}

	/**
	 * 准备配置数据
	 * @param configData 
	 */
	protected abstract void prepareConfigData(Map configData) throws Throwable;


	@Override
	public Set<String> getPropertyNames() {
		return source.keySet();
	}

	@Override
	public String getValue(String propertyName) {
		return source.get(propertyName);
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public final int getOrdinal() {
		return ordinal;
	}
}
