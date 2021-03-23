package org.dongx.configuration.microprofile.config.converter;

/**
 * TODO
 *
 * @author <a href="mailto:dongxiang886@gmail.com>Dongx</a>
 * @since
 */
public class ShortConverter extends AbstractConverter<Short> {
	
	@Override
	protected Short doConvert(String value) {
		return Short.valueOf(value);
	}
}
