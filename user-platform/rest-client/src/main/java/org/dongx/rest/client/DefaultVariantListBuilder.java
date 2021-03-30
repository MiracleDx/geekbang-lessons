package org.dongx.rest.client;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Variant;
import java.util.List;
import java.util.Locale;

/**
 * TODO
 *
 * @author <a href="mailto:dongxiang886@gmail.com>Dongx</a>
 * @since
 */
public class DefaultVariantListBuilder extends Variant.VariantListBuilder {
	
	@Override
	public List<Variant> build() {
		return null;
	}

	@Override
	public Variant.VariantListBuilder add() {
		return null;
	}

	@Override
	public Variant.VariantListBuilder languages(Locale... languages) {
		return null;
	}

	@Override
	public Variant.VariantListBuilder encodings(String... encodings) {
		return null;
	}

	@Override
	public Variant.VariantListBuilder mediaTypes(MediaType... mediaTypes) {
		return null;
	}
}
