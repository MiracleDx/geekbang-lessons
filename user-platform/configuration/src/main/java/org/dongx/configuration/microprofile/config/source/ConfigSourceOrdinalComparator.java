package org.dongx.configuration.microprofile.config.source;

import org.eclipse.microprofile.config.spi.ConfigSource;

import java.util.Comparator;

/**
 * {@link ConfigSource} 优先级比较器
 *
 * @author <a href="mailto:dongxiang886@gmail.com>Dongx</a>
 * @since
 */
public class ConfigSourceOrdinalComparator implements Comparator<ConfigSource> {
	
	public static final Comparator<ConfigSource> INSTANCE = new ConfigSourceOrdinalComparator();
	
	private ConfigSourceOrdinalComparator() {
		
	}
	
	@Override
	public int compare(ConfigSource o1, ConfigSource o2) {
		return Integer.compare(o2.getOrdinal(), o1.getOrdinal());
	}
}
