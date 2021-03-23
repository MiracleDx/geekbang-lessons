package org.dongx.configuration.microprofile.config.converter;

/**
 * String to int
 *
 * @author <a href="mailto:dongxiang886@gmail.com>Dongx</a>
 * @since
 */
public class IntegerConverter extends AbstractConverter<Integer> {
	
	@Override
	protected Integer doConvert(String value) {
		return Integer.valueOf(value);
	}
}
