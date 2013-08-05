package com.kaanha.migrate.core;

import java.io.IOException;

import junit.framework.TestCase;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.JavaType;
import org.junit.Test;

import com.kaanha.migrate.core.domain.rally.Project;
import com.kaanha.migrate.core.domain.rally.QueryResults;

public class JacksonTest extends TestCase {

	@Test
	public void testJsonToPojo() {
		ObjectMapper mapper = new ObjectMapper(); // can reuse, share globally
		String json = "{\"QueryResult\": {\"_rallyAPIMajor\": \"2\", \"_rallyAPIMinor\": \"0\", \"Errors\": [], \"Warnings\": [], \"TotalResultCount\": 1, \"StartIndex\": 1, \"PageSize\": 20, \"Results\": [{\"_rallyAPIMajor\": \"2\", \"_rallyAPIMinor\": \"0\", \"_ref\": \"https://rally1.rallydev.com/slm/webservice/v2.0/project/12849297519\", \"_refObjectName\": \"Sample Project\", \"_type\": \"Project\"}]}}";

		JavaType javaType = mapper.getTypeFactory().constructParametricType(QueryResults.class, Project.class);
		try {
			QueryResults<Project> queryResults = mapper.readValue(json, javaType);
			assertEquals(2, queryResults.getQueryResult().getRallyAPIMajor().longValue());
			assertEquals("Sample Project", queryResults.getQueryResult().getResults().get(0).getRefObjectName());
			assertEquals(Project.class, queryResults.getQueryResult().getResults().get(0).getClass());

		} catch (JsonParseException e) {
			fail(e.getMessage());
		} catch (JsonMappingException e) {
			fail(e.getMessage());
		} catch (IOException e) {
			fail(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}

	}
}
