package com.kaanha.migrate.core.api.rest;

import java.net.URISyntaxException;

import junit.framework.TestCase;

import org.junit.Test;
import org.springframework.web.client.RestClientException;

import com.kaanha.migrate.core.api.rest.RestApi;

public class RestApiTest extends TestCase {

	@Test
	public void testAuthentication() {

		RestApi restClient = new RestApi("rally.user.2@gmail.com", "RallyUser123!");
		try {
			restClient.get("https://rally1.rallydev.com/slm/webservice/v2.0/project");
		} catch (RestClientException e) {
			fail(e.getMessage());
		} catch (URISyntaxException e) {
			fail(e.getMessage());
		}

	}

}
