package com.kaanha.core.migrate.metadata;

import java.io.IOException;
import java.net.URISyntaxException;

import org.springframework.web.client.RestClientException;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.kaanha.migrate.core.api.JiraApi;

public class JIRAMetadataReader {

	private JiraApi jira;

	public JIRAMetadataReader(JiraApi jira) {
		this.jira = jira;

	}

	public JsonObject readProjectMetadata() throws URISyntaxException,
			IOException {
		JsonObject out = new JsonObject();
		JsonObject results = jira.search("issue/createmeta");
		JsonArray projects = results.get("projects").getAsJsonArray();
		if (projects.size() == 0) {
			createProjectMetdata(out);
		} else {
			createProjectMetdata(out);
		}
		return out;
	}

	private void createProjectMetdata(JsonObject out) {
		out.addProperty("name", "Project");
		out.addProperty("displayName", "Project");
		JsonArray primitives = new JsonArray();
		JsonObject name = new JsonObject();
		name.addProperty("id", "Name");
		name.addProperty("name", "name");
		name.addProperty("type", "STRING");
		primitives.add(name);
		JsonObject key = new JsonObject();
		key.addProperty("id", "Key");
		key.addProperty("name", "key");
		key.addProperty("type", "STRING");
		primitives.add(key);
		JsonObject description = new JsonObject();
		description.addProperty("id", "Description");
		description.addProperty("name", "desc");
		description.addProperty("type", "STRING");
		primitives.add(description);
		out.add("primitives", primitives);
	}

	public JsonObject readUserStoryMetadata() throws URISyntaxException,
			IOException {
		return readMetadata("Hierarchical Requirement");
	}

	private JsonObject readMetadata(String workspaceObjectName)
			throws IOException, RestClientException, URISyntaxException {

		return null;
	}

}
