package com.kaanha.migrate.core.api.rest;

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

public class RestApi {

	private RestTemplate restTemplate;

	public RestApi(String username, String password) {
		restTemplate = new RestTemplate();
		UsernamePasswordCredentials cred = new UsernamePasswordCredentials(username, password);
		BasicCredentialsProvider cp = new BasicCredentialsProvider();
		cp.setCredentials(AuthScope.ANY, cred);
		DefaultHttpClient client = new DefaultHttpClient();
		client.setCredentialsProvider(cp);
		ClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(client);

		restTemplate = new RestTemplate(factory);
	}

	public String get(String url) throws RestClientException, URISyntaxException {
		return restTemplate.getForObject(new URI(url), String.class);
	}

}
