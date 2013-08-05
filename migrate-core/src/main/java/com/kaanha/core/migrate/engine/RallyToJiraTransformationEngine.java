package com.kaanha.core.migrate.engine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.kaanha.migrate.core.persistence.domain.ArtifactType;
import com.kaanha.migrate.core.persistence.domain.SystemArtifactAttribute;
import com.kaanha.migrate.core.persistence.domain.SystemX;

public class RallyToJiraTransformationEngine extends TransformationEngine {

	public RallyToJiraTransformationEngine(SystemX source, SystemX target) {
		super(source, target);
	}

	public JsonObject transform(JsonObject sourceObject, ArtifactType artifactType) {
		System.out.println("Transform: " + sourceObject);

		switch (artifactType) {
		case PROJECT: {
			return toProject(sourceObject);
		}
		default: {
			return null;
		}
		}
	}

	private JsonObject toProject(JsonObject sourceObject) {
		String key = "PROJ";
		Map<String, String> map = new HashMap<String, String>();
		map.put("key", key);
		JsonObject json = mapValues(sourceObject, map);
		return json;
	}

	private JsonObject mapValues(JsonObject sourceObject, Map<String, String> map) {
		List<SystemArtifactAttribute> attributes = target.getArtifactofType(ArtifactType.PROJECT).getAttributes();
		for (SystemArtifactAttribute attribute : attributes) {
			JsonElement attributeValue = sourceObject.get(attribute.getAttributeMapping(source));
			if (!attributeValue.isJsonNull()) {
				String strValue = "";
				if (attributeValue.isJsonObject()) {
					JsonObject attributeValueObject = attributeValue.getAsJsonObject();
					attributeValue = attributeValueObject.get("_refObjectName");
					if (!attributeValue.isJsonNull()) {
						strValue = attributeValue.getAsString();
					}
				} else {
					strValue = attributeValue.getAsString();
				}
				if (StringUtils.isNotBlank(strValue)) {
					map.put(attribute.getName(), strValue);
				}
			}
		}
		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		JsonObject json = (JsonObject) (new JsonParser()).parse(gson.toJson(map));
		return json;
	}
}
