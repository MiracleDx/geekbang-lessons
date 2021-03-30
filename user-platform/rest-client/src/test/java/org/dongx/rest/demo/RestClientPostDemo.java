package org.dongx.rest.demo;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

/**
 * TODO
 *
 * @author <a href="mailto:dongxiang886@gmail.com>Dongx</a>
 * @since
 */
public class RestClientPostDemo {

	public static void main(String[] args) {
		User user = new User();
		user.setName("test post");
		user.setAge(18);

		Client client = ClientBuilder.newClient();
		Response response = client.target("http://127.0.0.1:8080/post")
				.request()
				.header("Content-Type", "application/json")
				.post(Entity.json(user));

		User result = response.readEntity(User.class);
		System.out.println(result);
	}
}
