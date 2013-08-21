package com.kaanha.migrate.core.api.rest;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.client.RestClientException;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.kaanha.migrate.core.persistence.api.DBRepository;
import com.kaanha.migrate.core.persistence.domain.ArtifactType;
import com.kaanha.migrate.core.persistence.domain.SystemX;

public class RallyReadApi extends RallyRestApi {

	private DBRepository dbRepository;
	SystemX system;

	public RallyReadApi(String url, String username, String password, DBRepository dbRepository) throws URISyntaxException {
		super(url, username, password);
		this.dbRepository = dbRepository;
		system = this.dbRepository.findSystemByName("Rally");
	}

	public JsonArray getSubscriptions() throws RestClientException, URISyntaxException, IOException {
		return searchObjects(system.getArtifactofType(ArtifactType.SUBSCRIPTION).getName(), null, system.getArtifactofType(ArtifactType.SUBSCRIPTION).getAttributeNames());
	}

	public JsonObject getObjectFromRef(String ref) throws RestClientException, URISyntaxException {
		return getObjectFromRef(ref, null);
	}

	public JsonArray getParentLevelUserStories(JsonObject project) throws IOException {
		List<String> dataElements = system.getArtifactofType(ArtifactType.USER_STORY).getAttributeNames();
		String objectType = system.getArtifactofType(ArtifactType.USER_STORY).getName();
		Map<String, String> filter = new LinkedHashMap<String, String>();
		filter.put("Project.ObjectID", project.get("ObjectID").getAsString());
		filter.put("Parent", "null");
		return searchObjects(objectType, filter, dataElements);
	}

	public JsonArray getWorkspaceProjects(JsonElement workspace) throws RestClientException, URISyntaxException {
		return getCollection(workspace, "Projects");
	}

	private JsonObject getObjectFromRef(String ref, List<String> dataElements) throws RestClientException, URISyntaxException {
		if (dataElements != null) {
			ref = ref + "?fetch=" + StringUtils.join(dataElements, ",");
		}
		System.out.println(ref);
		return findOne(ref);
	}

	public JsonObject getProjectDetails(JsonElement project) throws RestClientException, URISyntaxException {
		String ref = project.getAsJsonObject().get("_ref").getAsString();
		return getObjectFromRef(ref, system.getArtifactofType(ArtifactType.PROJECT).getAttributeNames()).get("Project").getAsJsonObject();
	}

	public JsonArray getSubscriptionWorkspaces(JsonElement subscription) throws RestClientException, URISyntaxException {
		return getCollection(subscription, "Workspaces");
	}

	private JsonArray getCollection(JsonElement obj, String collectionName) throws URISyntaxException {
		return getObjectFromRef(obj.getAsJsonObject().get(collectionName).getAsJsonObject().get("_ref").getAsString()).getAsJsonObject("QueryResult").getAsJsonArray("Results");
	}

	public JsonArray getUserStoryDefects(JsonElement userStory) throws URISyntaxException {
		return getCollection(userStory, "Defects");
	}

	public JsonArray getUserStoryTasks(JsonElement userStory) throws URISyntaxException {
		return getCollection(userStory, "Tasks");
	}

	public JsonArray getUserStoryAttachments(JsonElement userStory) throws URISyntaxException {
		return getCollection(userStory, "Attachments");
	}

}
