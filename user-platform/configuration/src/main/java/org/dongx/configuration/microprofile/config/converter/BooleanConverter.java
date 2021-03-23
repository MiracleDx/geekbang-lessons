package org.dongx.configuration.microprofile.config.converter;

/**
 * TODO
 *
 * @author <a href="mailto:dongxiang886@gmail.com>Dongx</a>
 * @since
 */
public class BooleanConverter extends AbstractConverter<Boolean> {
	
	@Override
	protected Boolean doConvert(String value) {
		return Boolean.parseBoolean(value);
	}
}
