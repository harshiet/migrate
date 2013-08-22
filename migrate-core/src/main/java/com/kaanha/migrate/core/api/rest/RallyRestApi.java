package com.kaanha.migrate.core.api.rest;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.client.RestClientException;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.kaanha.migrate.core.persistence.api.DBRepository;
import com.kaanha.migrate.core.persistence.domain.ArtifactType;
import com.kaanha.migrate.core.persistence.domain.SystemX;
import com.rallydev.rest.request.QueryRequest;
import com.rallydev.rest.response.QueryResponse;
import com.rallydev.rest.util.QueryFilter;

public class RallyRestApi extends RestApi {

	// private ObjectMapper mapper = new ObjectMapper();
	private JsonParser jsonParser = new JsonParser();
	private com.rallydev.rest.RallyRestApi api;
	private DBRepository dbRepository;
	private SystemX system;

	protected RallyRestApi(String url, String username, String password) throws URISyntaxException {
		super(username, password);
		try {
			api.close();
		} catch (Exception e) {

		}
		api = new com.rallydev.rest.RallyRestApi(new URI("https://" + new URI(url).getHost()), username, password);
		dbRepository = DBRepository.getInstance();
		system = this.dbRepository.findSystemByName("Rally");
	}

	//
	// public JsonArray findAll(String name) throws RestClientException,
	// URISyntaxException {
	// String json = rest.get(url + name);
	// JsonElement jsonElement = jsonParser.parse(json);
	// if (jsonElement.isJsonArray()) {
	// return (JsonArray) jsonElement;
	// } else {
	// if (jsonElement.isJsonObject()) {
	// JsonArray jsonArray = new JsonArray();
	// jsonArray.add(jsonElement);
	// return jsonArray;
	// }
	// }
	//
	// throw new RestClientException("Could not parse response from: " + json);
	// }

	protected JsonObject findOne(String ref) throws RestClientException, URISyntaxException {
		String json = get(ref);
		JsonObject jsonObject = jsonParser.parse(json).getAsJsonObject();
		return jsonObject;
	}

	// JavaType javaType =
	// mapper.getTypeFactory().constructParametricType(QueryResults.class,
	// objectType.getClazz());
	// QueryResults<T> queryResults = mapper.readValue(json, javaType);
	// return queryResults.getQueryResult().getResults();
	// public <T extends DomainObject> T fetch(ArtifactType objectType, String
	// ref) throws RestClientException,
	// URISyntaxException, JsonParseException, JsonMappingException, IOException
	// {
	// String json = rest.get(ref);
	// JavaType javaType =
	// mapper.getTypeFactory().constructParametricType(FetchResult.class,
	// objectType.getClazz());
	// T obj = mapper.readValue(json, javaType);
	// return obj;
	//
	// }

	// protected JsonArray searchObjects(ArtifactType artifactType, Map<String,
	// String> filter,
	// List<String> dataElements) throws IOException {
	protected JsonArray searchObjects(ArtifactType artifactType, Map<String, String> filter) throws IOException {

		String objectCode = system.getArtifactofType(artifactType).getName();
		List<String> dataElements = system.getArtifactofType(artifactType).getAttributeNames();

		QueryRequest request = new QueryRequest(objectCode);
		request.getFetch().add("true");
		request.getFetch().addAll(dataElements);

		if (filter != null) {
			QueryFilter queryFilter = null;
			for (String filterName : filter.keySet()) {
				QueryFilter tempFilter = new QueryFilter(filterName, "=",
						filter.get(filterName));
				if (queryFilter == null) {
					queryFilter = tempFilter;
				} else {
					queryFilter = queryFilter.and(tempFilter);
				}
			}
			request.setQueryFilter(queryFilter);
		}
		request.setLimit(100000);
		QueryResponse queryResponse = api.query(request);
		System.out.println(request.toUrl());
		return queryResponse.getResults();

	}

	protected JsonArray searchObjects(ArtifactType artifactType) throws IOException {
		return searchObjects(artifactType, null);
	}

	protected JsonObject getObjectFromRef(String ref) throws RestClientException, URISyntaxException {
		return getObjectFromRef(ref, null);
	}

	protected JsonObject getObjectFromRef(String ref, ArtifactType artifactType) throws RestClientException, URISyntaxException {
		if (artifactType != null) {
			List<String> dataElements = system.getArtifactofType(artifactType).getAttributeNames();
			ref = ref + "?fetch=" + StringUtils.join(dataElements, ",");
		}
		System.out.println(ref);
		return findOne(ref);
	}

	protected JsonArray getCollection(JsonElement obj, String collectionName) throws URISyntaxException {
		return getObjectFromRef(obj.getAsJsonObject().get(collectionName).getAsJsonObject().get("_ref").getAsString()).getAsJsonObject("QueryResult").getAsJsonArray("Results");
	}
}
