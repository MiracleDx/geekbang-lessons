package org.dongx.configuration.microprofile.config.source;

import org.eclipse.microprofile.config.spi.ConfigSource;

import java.util.*;
import java.util.stream.Stream;

import static java.util.ServiceLoader.load;

/**
 * TODO
 *
 * @author <a href="mailto:dongxiang886@gmail.com>Dongx</a>
 * @since
 */
public class ConfigSources implements Iterable<ConfigSource> {
	
	private boolean addedDefaultConfigSources;
	
	private boolean addedDiscoveredConfigSources;
	
	private List<ConfigSource> configSources = new LinkedList<>();
	
	private ClassLoader classLoader;
	
	public ConfigSources(ClassLoader classLoader) {
		this.classLoader = classLoader;
	}
	
	public void addDefaultSources() {
		if (addedDefaultConfigSources) {
			return;
		}

		addConfigSources(JavaSystemPropertiesConfigSource.class,
				OperationSystemEnvironmentVariablesConfigSource.class,
				DefaultResourceConfigSource.class);
		addedDefaultConfigSources = true;
	}

	public void addDiscoveredSources() {
		if (addedDiscoveredConfigSources) {
			return;
		}

		addConfigSources(load(ConfigSource.class, classLoader));
		addedDiscoveredConfigSources = true;
	}


	public void addConfigSources(Class<? extends ConfigSource>... configSourceClasses) {
		addConfigSources(
				Stream.of(configSourceClasses)
				.map(this::newInstance)
				.toArray(ConfigSource[]::new)
		);
	}
	
	public void addConfigSources(ConfigSource... configSources) {
		addConfigSources(Arrays.asList(configSources));
	}

	public void addConfigSources(Iterable<ConfigSource> configSources) {
		configSources.forEach(this.configSources::add);
		Collections.sort(this.configSources, ConfigSourceOrdinalComparator.INSTANCE);
	}
	
	private ConfigSource newInstance(Class<? extends ConfigSource> configSourceClass) {
		ConfigSource instance = null;
		try {
			instance = configSourceClass.newInstance();
		}  catch (InstantiationException | IllegalAccessException e) {
			throw new IllegalStateException(e);
		}
		return instance;
	}

	@Override
	public Iterator<ConfigSource> iterator() {
		return configSources.iterator();
	}

	public void setClassLoader(ClassLoader classLoader) {
		this.classLoader = classLoader;
	}

	public boolean isAddedDefaultConfigSources() {
		return addedDefaultConfigSources;
	}

	public boolean isAddedDiscoveredConfigSources() {
		return addedDiscoveredConfigSources;
	}

	public ClassLoader getClassLoader() {
		return classLoader;
	}
}
