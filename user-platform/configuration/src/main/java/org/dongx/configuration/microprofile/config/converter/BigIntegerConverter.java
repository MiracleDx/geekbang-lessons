package org.dongx.configuration.microprofile.config.converter;

import java.math.BigInteger;

/**
 * TODO
 *
 * @author <a href="mailto:dongxiang886@gmail.com>Dongx</a>
 * @since
 */
public class BigIntegerConverter extends AbstractConverter<BigInteger> {

	@Override
	protected BigInteger doConvert(String value) {
		return new BigInteger(value);
	}
}

