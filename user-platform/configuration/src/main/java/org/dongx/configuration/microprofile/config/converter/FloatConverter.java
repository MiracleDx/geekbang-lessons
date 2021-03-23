package org.dongx.configuration.microprofile.config.converter;

/**
 * String to long
 *
 * @author <a href="mailto:dongxiang886@gmail.com>Dongx</a>
 * @since
 */
public class FloatConverter extends AbstractConverter<Float> {
	
	@Override
	protected Float doConvert(String value) {
		return  Float.valueOf(value);
	}
}
