package com.kaanha.migrate.core;

import java.io.IOException;
import java.net.URISyntaxException;

import org.springframework.web.client.RestClientException;

import com.kaanha.core.migrate.engine.RallyToJiraEngine;

public class App {
	

	public static void main(String[] args) throws RestClientException, URISyntaxException, IOException {
		RallyToJiraEngine engine = new RallyToJiraEngine();
		engine.migrate();
		
	}
}
