package org.dongx.configuration.microprofile.config.convert;

/**
 * String to long
 *
 * @author <a href="mailto:dongxiang886@gmail.com>Dongx</a>
 * @since
 */
public class FloatConverter implements org.eclipse.microprofile.config.spi.Converter<Float> {
	
	@Override
	public Float convert(String value) throws IllegalArgumentException, NullPointerException {
		return Float.parseFloat(value);
	}
}
