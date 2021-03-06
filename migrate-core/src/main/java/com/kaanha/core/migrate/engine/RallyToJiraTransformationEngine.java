package com.kaanha.core.migrate.engine;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.kaanha.migrate.core.persistence.domain.ArtifactType;
import com.kaanha.migrate.core.persistence.domain.SystemX;

public class RallyToJiraTransformationEngine extends TransformationEngine {
	private static Logger logger = Logger.getLogger(Thread.currentThread().getStackTrace()[0].getClassName());

	public RallyToJiraTransformationEngine(SystemX source, SystemX target) {
		super(source, target);
	}

	public JsonObject transform(JsonObject sourceObject, ArtifactType artifactType) {
		System.out.println("Transform: " + sourceObject);

		switch (artifactType) {
		case PROJECT: {
			return toProject(sourceObject);
		}
		case USER_STORY: {
			return toUserStory(sourceObject);
		}
		default: {
			return null;
		}
		}
	}

	private JsonObject toUserStory(JsonObject sourceObject) {
		Map<String, String> map = new HashMap<String, String>();
		JsonObject json = mapValues(sourceObject, map, ArtifactType.USER_STORY);
		return json;
	}

	private JsonObject toProject(JsonObject sourceObject) {
		String key = "PROJ";
		Map<String, String> map = new HashMap<String, String>();
		map.put("key", key);
		JsonObject json = mapValues(sourceObject, map, ArtifactType.PROJECT);
		return json;
	}

	private JsonObject mapValues(JsonObject sourceObject, Map<String, String> map, ArtifactType artifactType) {
		// Collection<String> attributes =
		// target.getAttributesFor(artifactType).values();
		// for (String attribute : attributes) {
		// SystemAttribute mappedAttribute =
		// attribute.getAttributeMapping(source);
		// if (mappedAttribute != null) {
		// String attributeMapping = mappedAttribute.getName();
		// JsonElement attributeValue = sourceObject.get(attributeMapping);
		//
		// while (mappedAttribute.hasChild()) {
		// if (attributeValue == null) {
		// logger.warning("Null " + attributeMapping);
		// break;
		// }
		// if (attributeValue.isJsonNull()) {
		// break;
		// }
		// mappedAttribute = mappedAttribute.getChildAttribute();
		// attributeMapping = mappedAttribute.getName();
		// attributeValue =
		// attributeValue.getAsJsonObject().get(attributeMapping);
		// }
		// if (attributeValue != null &&
		// StringUtils.isNotBlank(attributeValue.getAsString())) {
		// map.put(attribute.getName(), attributeValue.getAsString());
		// }
		// }
		// }
		//
		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		JsonObject json = (JsonObject) (new JsonParser()).parse(gson.toJson(map));
		return json;
	}
}
