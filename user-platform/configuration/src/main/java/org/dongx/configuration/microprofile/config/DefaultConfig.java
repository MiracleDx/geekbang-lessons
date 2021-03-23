package org.dongx.configuration.microprofile.config;

import org.dongx.configuration.microprofile.config.converter.Converters;
import org.dongx.configuration.microprofile.config.source.ConfigSources;
import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.ConfigValue;
import org.eclipse.microprofile.config.spi.ConfigSource;
import org.eclipse.microprofile.config.spi.Converter;

import java.util.*;
import java.util.stream.StreamSupport;

/**
 * TODO
 *
 * @author <a href="mailto:dongxiang886@gmail.com>Dongx</a>
 * @since
 */
public class DefaultConfig implements Config {
	
	private final ConfigSources configSources;

	private final Converters converters;

	public DefaultConfig(ConfigSources configSources, Converters converters) {
		this.configSources = configSources;
		this.converters = converters;
	}

	@Override
	public <T> T getValue(String propertyName, Class<T> propertyType) {
		String propertyValue = getPropertyValue(propertyName);
		// String 转换成目标类型
		Converter<T> converter = doGetConverter(propertyType);
		return converter == null ? null : converter.convert(propertyValue);
	}

	@Override
	public ConfigValue getConfigValue(String propertyName) {
		return null;
	}

	@Override
	public <T> Optional<T> getOptionalValue(String propertyName, Class<T> propertyType) {
		return Optional.empty();
	}

	@Override
	public Iterable<String> getPropertyNames() {
		return StreamSupport.stream(configSources.spliterator(), false)
				.map(ConfigSource::getPropertyNames)
				.collect(LinkedHashSet::new, Set::addAll, Set::addAll);
	}

	@Override
	public Iterable<ConfigSource> getConfigSources() {
		// todo 返回不可变集合
		return configSources;
	}

	@Override
	public <T> Optional<Converter<T>> getConverter(Class<T> forType) {
		Converter converter = doGetConverter(forType);
		return converter == null ? Optional.empty() : Optional.of(converter);
	}

	@Override
	public <T> T unwrap(Class<T> type) {
		return null;
	}

	protected String getPropertyValue(String propertyName) {
		String propertyValue = null;
		for (ConfigSource configSource : configSources) {
			propertyValue = configSource.getValue(propertyName);
			if (propertyValue != null) {
				break;
			}
		}
		return propertyValue;
	}

	private <T> Converter<T> doGetConverter(Class<T> propertyType) {
		List<Converter> converters = this.converters.getConverters(propertyType);
		return converters.isEmpty() ? null : converters.get(0);
	}
}
