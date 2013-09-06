package com.kaanha.core.migrate.metadata;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.client.RestClientException;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.kaanha.migrate.core.api.RallyApi;
import com.kaanha.migrate.core.persistence.domain.ArtifactType;

public class RallyMetadataReader {
	private RallyApi rally;

	public RallyMetadataReader(RallyApi rally) {
		this.rally = rally;

	}

	public JsonObject readProjectMetadata() throws URISyntaxException,
			IOException {
		return readMetadata("Project");
	}

	public JsonObject readUserStoryMetadata() throws URISyntaxException,
			IOException {
		return readMetadata("Hierarchical Requirement");
	}

	private JsonObject readMetadata(String workspaceObjectName)
			throws IOException, RestClientException, URISyntaxException {

		Map<String, String> filter = new HashMap<String, String>();
		filter.put("Name", workspaceObjectName);
		JsonArray metadataResults = rally.searchObjects(ArtifactType.TYPE_DEFINITION,
				filter);
		JsonObject metadata = metadataResults.get(0).getAsJsonObject();
		JsonArray arrAttributes = rally.getCollection(metadata, "Attributes");
		JsonObject out = new JsonObject();
		out.addProperty("name", metadata.get("_refObjectName").getAsString());
		out.addProperty("displayName", metadata.get("DisplayName")
				.getAsString());
		// JsonArray attributes = new JsonArray();
		JsonArray primitiveAttributes = new JsonArray();
		JsonArray collectionAttributes = new JsonArray();
		JsonArray objectAttributes = new JsonArray();

		for (JsonElement eleAttribute : arrAttributes) {
			JsonObject objAttribute = eleAttribute.getAsJsonObject();
			JsonObject attribute = new JsonObject();
			attribute.addProperty("id", objAttribute.get("ElementName")
					.getAsString());
			attribute.addProperty("name", objAttribute.get("Name")
					.getAsString());
			String attributeType = objAttribute.get("AttributeType")
					.getAsString();
			attribute.addProperty("type", attributeType);
			int countOfAllowedValues = objAttribute.get("AllowedValues")
					.getAsJsonObject().get("Count").getAsInt();
			if (countOfAllowedValues > 0) {
				JsonArray allowedValues = new JsonArray();
				JsonArray arrAllowedValues = rally.getCollection(objAttribute,
						"AllowedValues");
				for (JsonElement eleAllowedValue : arrAllowedValues) {
					String strAllowedValue = eleAllowedValue.getAsJsonObject()
							.get("StringValue").getAsString();
					JsonObject allowedValue = new JsonObject();
					allowedValue.addProperty("value", strAllowedValue);
					allowedValues.add(allowedValue);
				}
				attribute.add("allowedValues", allowedValues);
			}
			if (attributeType.equals("COLLECTION")) {
				collectionAttributes.add(attribute);
			} else {
				if (attributeType.equals("OBJECT")) {
					objectAttributes.add(attribute);
				} else {
					primitiveAttributes.add(attribute);
				}
			}
		}

		// out.add("attributes", attributes);
		out.add("primitives", primitiveAttributes);
		out.add("collections", collectionAttributes);
		out.add("objects", objectAttributes);

		return out;

	}

}
