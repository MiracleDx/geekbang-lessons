package org.dongx.configuration.microprofile.config.converter;

/**
 * String to String
 *
 * @author <a href="mailto:dongxiang886@gmail.com>Dongx</a>
 * @since
 */
public class StringConverter extends AbstractConverter<String> {

	@Override
	protected String doConvert(String value) {
		return value;
	}
}
