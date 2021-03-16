package org.dongx.configuration.microprofile.config.convert;

/**
 * String to long
 *
 * @author <a href="mailto:dongxiang886@gmail.com>Dongx</a>
 * @since
 */
public class LongConverter implements org.eclipse.microprofile.config.spi.Converter<Long> {
	
	@Override
	public Long convert(String value) throws IllegalArgumentException, NullPointerException {
		return Long.parseLong(value);
	}
}
