package org.dongx.configuration.microprofile.config.convert;

/**
 * String to double
 *
 * @author <a href="mailto:dongxiang886@gmail.com>Dongx</a>
 * @since
 */
public class DoubleConverter implements org.eclipse.microprofile.config.spi.Converter<Double> {
	
	@Override
	public Double convert(String value) throws IllegalArgumentException, NullPointerException {
		return Double.parseDouble(value);
	}
}
