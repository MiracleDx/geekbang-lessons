package org.dongx.configuration.microprofile.config.converter;

/**
 * String to long
 *
 * @author <a href="mailto:dongxiang886@gmail.com>Dongx</a>
 * @since
 */
public class LongConverter extends AbstractConverter<Long> {

	@Override
	protected Long doConvert(String value) {
		return Long.valueOf(value);
	}
}
