package org.dongx.configuration.microprofile.config;

import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.ConfigValue;
import org.eclipse.microprofile.config.spi.ConfigSource;
import org.eclipse.microprofile.config.spi.Converter;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

/**
 * TODO
 *
 * @author <a href="mailto:dongxiang886@gmail.com>Dongx</a>
 * @since
 */
public class JavaConfig implements Config {

	/**
	 * 内部可变的集合，不要直接暴露在外面
	 */
	private List<ConfigSource> configSources = new LinkedList<>();

	/**
	 * 转换器集合
	 */
	private Map<Class, Converter> convertersMap = new HashMap<>(); 
	
	private static Comparator<ConfigSource> configSourceComparator = new Comparator<ConfigSource>() {
		@Override
		public int compare(ConfigSource o1, ConfigSource o2) {
			// 从小到大排序
			return Integer.compare(o2.getOrdinal(), o1.getOrdinal());
		}
	};
	
	public JavaConfig() {
		ClassLoader classLoader = getClass().getClassLoader();
		ServiceLoader<ConfigSource> serviceLoader = ServiceLoader.load(ConfigSource.class, classLoader);
		serviceLoader.forEach(configSources::add);
		// 排序
		configSources.sort(configSourceComparator);
		
		// 获取转换器
		ServiceLoader<Converter> converters = ServiceLoader.load(Converter.class);
		Iterator<Converter> iterator = converters.iterator();
		while (iterator.hasNext()) {
			Converter converter = iterator.next();
			Type[] types = converter.getClass().getGenericInterfaces();
			ParameterizedType parameterized  = (ParameterizedType) types[0];
			Class clazz = (Class) parameterized.getActualTypeArguments()[0];
			convertersMap.put(clazz, converter);
		}
	}

	/**
	 * 获取配置的最小作用域的属性
	 * @param propertyName 属性名称
	 * @return
	 */
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

	@Override
	public <T> T getValue(String propertyName, Class<T> propertyType) {
		String properties = getPropertyValue(propertyName);
		// String 转化成目标类型
		Converter converter = convertersMap.get(propertyType);
		return (T) converter.convert(properties);
	}

	@Override
	public ConfigValue getConfigValue(String propertyName) {
		return null;
	}

	@Override
	public <T> Optional<T> getOptionalValue(String propertyName, Class<T> propertyType) {
		T value = getValue(propertyName, propertyType);
		return Optional.ofNullable(value);
	}

	@Override
	public Iterable<String> getPropertyNames() {
		return null;
	}

	@Override
	public Iterable<ConfigSource> getConfigSources() {
		return Collections.unmodifiableCollection(configSources);
	}

	@Override
	public <T> Optional<Converter<T>> getConverter(Class<T> forType) {
		return Optional.empty();
	}

	@Override
	public <T> T unwrap(Class<T> type) {
		return null;
	}
}
