package org.dongx.configuration.microprofile.config.converter;

/**
 * TODO
 *
 * @author <a href="mailto:dongxiang886@gmail.com>Dongx</a>
 * @since
 */
public class ByteConverter extends AbstractConverter<Byte> {
	
	@Override
	protected Byte doConvert(String value) {
		return Byte.valueOf(value);
	}
}
