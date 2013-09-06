package com.kaanha.migrate.core.api;

import java.net.URI;
import java.net.URISyntaxException;

import javax.xml.rpc.ServiceException;

import org.springframework.web.client.RestClientException;
import org.swift.common.soap.jira.JiraSoapService;
import org.swift.common.soap.jira.JiraSoapServiceServiceLocator;
import org.swift.common.soap.jira.RemoteAuthenticationException;
import org.swift.common.soap.jira.RemoteException;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class JiraRestApi extends RestApi {

	private String url;
	private JsonParser jsonParser = new JsonParser();
	private JiraSoapServiceServiceLocator jiraSoapServiceGetter = new JiraSoapServiceServiceLocator();
	private JiraSoapService jiraSoapService = null;
	private String fToken = null;

	public JiraRestApi(String url, String username, String password)
			throws RestClientException, URISyntaxException, ServiceException, RemoteAuthenticationException, RemoteException, java.rmi.RemoteException {
		super(username, password);
		this.url = url + "/rest/api/latest/";
		String endPoint = "/rpc/soap/jirasoapservice-v2";
		jiraSoapServiceGetter.setJirasoapserviceV2EndpointAddress(new URI(
				"https://" + new URI(url).getHost()) + endPoint);
		jiraSoapServiceGetter.setMaintainSession(true);
		jiraSoapService = jiraSoapServiceGetter.getJirasoapserviceV2();
		fToken = jiraSoapService.login(username, password);
	}

	protected JsonObject search(String ref) throws RestClientException,
			URISyntaxException {
		if (!ref.contains("://")) {
			ref = url + ref;
		}
		String json = get(ref);
		return jsonParser.parse(json).getAsJsonObject();
	}

}
