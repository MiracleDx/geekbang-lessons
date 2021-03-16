package org.dongx.configuration.microprofile.config.convert;

/**
 * String to int
 *
 * @author <a href="mailto:dongxiang886@gmail.com>Dongx</a>
 * @since
 */
public class IntConverter implements org.eclipse.microprofile.config.spi.Converter<Integer> {
	
	@Override
	public Integer convert(String value) throws IllegalArgumentException, NullPointerException {
		return Integer.parseInt(value);
	}
}
