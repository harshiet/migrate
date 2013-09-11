package com.kaanha.migrate.core.api;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.web.client.RestClientException;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.kaanha.migrate.core.persistence.domain.ArtifactType;

public class RallyReadApi extends RallyApi {

	public RallyReadApi(String url, String username, String password)
			throws URISyntaxException, IOException {
		super(url, username, password);
	}

	public JsonArray getSubscriptions() throws RestClientException,
			URISyntaxException, IOException {
		return searchObjects(ArtifactType.SUBSCRIPTION);
	}

	public JsonArray getParentLevelUserStories(JsonObject project)
			throws IOException {
		Map<String, String> filter = new LinkedHashMap<String, String>();
		filter.put("Project.ObjectID", project.get("ObjectID").getAsString());
		filter.put("Parent", "null");
		return searchObjects(ArtifactType.USER_STORY, filter);
	}

	public JsonArray getWorkspaceProjects(JsonElement workspace)
			throws RestClientException, URISyntaxException {
		return getCollection(workspace, "Projects");
	}

	public JsonObject getProjectDetails(JsonElement project)
			throws RestClientException, URISyntaxException {
		String ref = project.getAsJsonObject().get("_ref").getAsString();
		return getObjectFromRef(ref, ArtifactType.PROJECT).get("Project")
				.getAsJsonObject();
	}

	public JsonArray getSubscriptionWorkspaces(JsonElement subscription)
			throws RestClientException, URISyntaxException {
		return getCollection(subscription, "Workspaces");
	}

	public JsonArray getUserStoryDefects(JsonElement userStory)
			throws URISyntaxException {
		return getCollection(userStory, "Defects");
	}

	public JsonArray getUserStoryTasks(JsonElement userStory)
			throws URISyntaxException {
		return getCollection(userStory, "Tasks");
	}

	public JsonArray getUserStoryAttachments(JsonElement userStory)
			throws URISyntaxException {
		return getCollection(userStory, "Attachments");
	}

}
