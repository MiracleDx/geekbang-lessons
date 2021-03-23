package org.dongx.configuration.microprofile.config.converter;

import org.eclipse.microprofile.config.spi.Converter;

/**
 * TODO
 *
 * @author <a href="mailto:dongxiang886@gmail.com>Dongx</a>
 * @since
 */
public class PrioritizedConverter<T> implements Converter<T>, Comparable<PrioritizedConverter<T>> {
	
	private final Converter<T> converter;
	
	private final int priority;
	
	public PrioritizedConverter(Converter<T> converter, int priority) {
		this.converter = converter;
		this.priority = priority;
	}

	@Override
	public T convert(String value) throws IllegalArgumentException, NullPointerException {
		return converter.convert(value);
	}
	
	@Override
	public int compareTo(PrioritizedConverter<T> other) {
		return Integer.compare(other.getPriority(), this.priority);
	}

	public Converter<T> getConverter() {
		return converter;
	}

	public int getPriority() {
		return priority;
	}
}
