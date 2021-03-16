package org.dongx.configuration.microprofile.config.convert;

/**
 * String to String
 *
 * @author <a href="mailto:dongxiang886@gmail.com>Dongx</a>
 * @since
 */
public class StringConverter implements org.eclipse.microprofile.config.spi.Converter<String> {
	
	@Override
	public String convert(String value) throws IllegalArgumentException, NullPointerException {
		return value;
	}
}
