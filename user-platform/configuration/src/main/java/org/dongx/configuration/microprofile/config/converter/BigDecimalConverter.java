package org.dongx.configuration.microprofile.config.converter;

import java.math.BigDecimal;

/**
 * TODO
 *
 * @author <a href="mailto:dongxiang886@gmail.com>Dongx</a>
 * @since
 */
public class BigDecimalConverter extends AbstractConverter<BigDecimal> {

	@Override
	protected BigDecimal doConvert(String value) {
		return new BigDecimal(value);
	}
}
