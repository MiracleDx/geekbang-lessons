package org.dongx.configuration.microprofile.config.converter;

import org.eclipse.microprofile.config.spi.Converter;

/**
 * TODO
 *
 * @author <a href="mailto:dongxiang886@gmail.com>Dongx</a>
 * @since
 */
public abstract class AbstractConverter<T> implements Converter<T> {
	
	@Override
	public T convert(String value) throws IllegalArgumentException, NullPointerException {
		if (value == null) {
			throw new NullPointerException("The value must not be null");
		}
		
		return doConvert(value);
	}

	protected abstract T doConvert(String value);
}
