package com.kaanha.migrate.core.api.rest;

import java.net.URISyntaxException;

import org.springframework.web.client.RestClientException;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class JiraRestApi extends RestApi {

	private String url;
	private JsonParser jsonParser = new JsonParser();

	public JiraRestApi(String url, String username, String password) {
		super(username, password);
		this.url = url + "/rest/api/latest/";

	}

	protected JsonObject search(String ref) throws RestClientException, URISyntaxException {
		if (!ref.contains("://")) {
			ref = url + ref;
		}
		String json = get(ref);
		return jsonParser.parse(json).getAsJsonObject();
	}

}
