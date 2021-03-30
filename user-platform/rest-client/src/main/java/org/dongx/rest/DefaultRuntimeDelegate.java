package org.dongx.rest;

import org.dongx.rest.client.DefaultVariantListBuilder;
import org.dongx.rest.core.DefaultResponseBuilder;
import org.dongx.rest.core.DefaultUriBuilder;

import javax.ws.rs.core.*;
import javax.ws.rs.ext.RuntimeDelegate;

/**
 * TODO
 *
 * @author <a href="mailto:dongxiang886@gmail.com>Dongx</a>
 * @since
 */
public class DefaultRuntimeDelegate extends RuntimeDelegate {

	@Override
	public UriBuilder createUriBuilder() {
		return new DefaultUriBuilder();
	}

	@Override
	public Response.ResponseBuilder createResponseBuilder() {
		return new DefaultResponseBuilder();
	}

	@Override
	public Variant.VariantListBuilder createVariantListBuilder() {
		return new DefaultVariantListBuilder();
	}

	@Override
	public <T> T createEndpoint(Application application, Class<T> endpointType) throws IllegalArgumentException, UnsupportedOperationException {
		return null;
	}

	@Override
	public <T> HeaderDelegate<T> createHeaderDelegate(Class<T> type) throws IllegalArgumentException {
		return null;
	}

	@Override
	public Link.Builder createLinkBuilder() {
		return null;
	}
}
