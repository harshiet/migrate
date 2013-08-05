package com.kaanha.migrate.core.rest.client;

import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

public class RestClient {

	private RestTemplate restTemplate;

	private RestClient() {
		restTemplate = new RestTemplate();
	}

	private RestClient(String username, String password) {
		UsernamePasswordCredentials cred = new UsernamePasswordCredentials(username, password);
		BasicCredentialsProvider cp = new BasicCredentialsProvider();
		cp.setCredentials(AuthScope.ANY, cred);
		DefaultHttpClient client = new DefaultHttpClient();
		client.setCredentialsProvider(cp);
		ClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(client);

		restTemplate = new RestTemplate(factory);
	}

	public static RestClient getRestClient() {
		RestClient restClient = new RestClient();
		return restClient;
	}

	public static RestClient getRestClientAuthenticated(String username, String password) {
		RestClient restClient = new RestClient(username, password);
		return restClient;
	}

	public String get(String url) throws RestClientException, URISyntaxException {
		return restTemplate.getForObject(new URI(url), String.class);
	}

}
