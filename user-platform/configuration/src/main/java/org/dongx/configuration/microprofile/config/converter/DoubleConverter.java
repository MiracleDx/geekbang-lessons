package org.dongx.configuration.microprofile.config.converter;

/**
 * String to double
 *
 * @author <a href="mailto:dongxiang886@gmail.com>Dongx</a>
 * @since
 */
public class DoubleConverter extends AbstractConverter<Double> {

	@Override
	protected Double doConvert(String value) {
		return Double.valueOf(value);
	}
}
