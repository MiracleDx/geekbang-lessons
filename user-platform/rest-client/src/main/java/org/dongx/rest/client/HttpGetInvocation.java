package org.dongx.rest.client;

import org.dongx.rest.core.DefaultResponse;

import javax.ws.rs.HttpMethod;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.InvocationCallback;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

/**
 * HTTP Get Method {@link javax.ws.rs.client.Invocation}
 *
 * @author <a href="mailto:dongxiang886@gmail.com>Dongx</a>
 * @since
 */
public class HttpGetInvocation implements Invocation {
	
	private final URI uri;
	
	private final URL url;
	
	private final MultivaluedMap<String, Object> headers;

	public HttpGetInvocation(URI uri, MultivaluedMap<String, Object> headers) {
		this.uri = uri;
		this.headers = headers;
		try {
			this.url = uri.toURL();
		} catch (MalformedURLException e) {
			throw new IllegalArgumentException(e);
		}
	}

	@Override
	public Invocation property(String name, Object value) {
		return this;
	}

	@Override
	public Response invoke() {
		HttpURLConnection connection = null;
		try {
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod(HttpMethod.GET);
			setRequestHeaders(connection);
			// todo Set the cookies
			int statusCode = connection.getResponseCode();

			DefaultResponse response = new DefaultResponse();
			response.setConnection(connection);
			response.setStatus(statusCode);

			Response.Status status = Response.Status.fromStatusCode(statusCode);
            //switch (status) {
			//	case Response.Status.OK:
            //        break;
            //    default:
            //        break;
            //}
			//
			return response;
		} catch (IOException e) {
			// TODO Error handler
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public <T> T invoke(Class<T> responseType) {
		Response response = invoke();
		return response.readEntity(responseType);
	}

	@Override
	public <T> T invoke(GenericType<T> responseType) {
		Response response = invoke();
		return response.readEntity(responseType);
	}

	@Override
	public Future<Response> submit() {
		return null;
	}

	@Override
	public <T> Future<T> submit(Class<T> responseType) {
		return null;
	}

	@Override
	public <T> Future<T> submit(GenericType<T> responseType) {
		return null;
	}

	@Override
	public <T> Future<T> submit(InvocationCallback<T> callback) {
		return null;
	}

	private void setRequestHeaders(HttpURLConnection connection) {
		for (Map.Entry<String, List<Object>> entry : headers.entrySet()) {
			String headerName = entry.getKey();
			for (Object headerValue : entry.getValue()) {
				connection.setRequestProperty(headerName, headerValue.toString());
			}
		}
	}
}
