package com.kaanha.migrate.core.api.rest;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.web.client.RestClientException;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.rallydev.rest.request.QueryRequest;
import com.rallydev.rest.response.QueryResponse;
import com.rallydev.rest.util.Fetch;
import com.rallydev.rest.util.QueryFilter;

public class RallyRestApi extends RestApi {

	ObjectMapper mapper = new ObjectMapper();
	JsonParser jsonParser = new JsonParser();
	com.rallydev.rest.RallyRestApi api;

	protected RallyRestApi(String url, String username, String password) throws URISyntaxException {
		super(username, password);
		try {
			api.close();
		} catch (Exception e) {

		}
		api = new com.rallydev.rest.RallyRestApi(new URI("https://" + new URI(url).getHost()), username, password);
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

	protected JsonArray searchObjects(String objectCode, Map<String, String> filter,
			List<String> dataElements) throws IOException {
		QueryRequest request = new QueryRequest(objectCode);
		request.setFetch(new Fetch(StringUtils.join(dataElements, ",")));
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
}
