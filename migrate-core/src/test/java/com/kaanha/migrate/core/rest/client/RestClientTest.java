package com.kaanha.migrate.core.rest.client;

import java.net.URISyntaxException;

import junit.framework.TestCase;

import org.junit.Test;
import org.springframework.web.client.RestClientException;

public class RestClientTest extends TestCase {

	@Test
	public void testAuthentication() {

		RestClient restClient = RestClient.getRestClientAuthenticated("rally.user.2@gmail.com", "RallyUser123!");
		try {
			restClient.get("https://rally1.rallydev.com/slm/webservice/v2.0/project");
		} catch (RestClientException e) {
			fail(e.getMessage());
		} catch (URISyntaxException e) {
			fail(e.getMessage());
		}

	}

}
