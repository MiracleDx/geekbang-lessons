package org.dongx.configuration.microprofile.config.converter;

/**
 * TODO
 *
 * @author <a href="mailto:dongxiang886@gmail.com>Dongx</a>
 * @since
 */
public class CharacterConverter extends AbstractConverter<Character> {

	@Override
	protected Character doConvert(String value) {
		if (value == null || value.isEmpty()) {
			return null;
		}
		return Character.valueOf(value.charAt(0));
	}
}

