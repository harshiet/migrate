package com.kaanha.migrate.core;

import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.web.client.RestClientException;

import com.googlecode.jsonschema2pojo.DefaultGenerationConfig;
import com.googlecode.jsonschema2pojo.Jackson2Annotator;
import com.googlecode.jsonschema2pojo.SchemaGenerator;
import com.googlecode.jsonschema2pojo.SchemaMapper;
import com.googlecode.jsonschema2pojo.SchemaStore;
import com.googlecode.jsonschema2pojo.SourceType;
import com.googlecode.jsonschema2pojo.rules.RuleFactory;
import com.kaanha.migrate.core.api.rest.RallyRestApi;
import com.kaanha.migrate.core.api.rest.RestApi;
import com.sun.codemodel.JCodeModel;

public class JsonToJavaTest {
	static RallyRestApi rally;
	static RestApi rest;
	static String username = "rally.user.2@gmail.com";
	static String password = "RallyUser123!";
	static String json;

	@BeforeClass
	public static void runBeforeClass() throws RestClientException, JsonParseException, JsonMappingException,
			URISyntaxException, IOException {
		// rally = new
		// RallyRestClient("https://rally1.rallydev.com/slm/webservice/v2.0/",
		// username, password);
		// rest = RestClient.getRestClientAuthenticated(username, password);
		// List<Project> projects = rally.findAll(ArtifactType.PROJECT);
		// String ref = projects.get(0).getRef();
		// json = rest.get(ref);
		// System.out.println(json);
	}

	@Test
	public void testJsonToJava() {

		JCodeModel codeModel = new JCodeModel();
		try {
			File f = new File(".");
			String schemaUrl = "file:///" + URLEncoder.encode(f.getCanonicalPath(), "UTF-8") + "/src/test/resources/jira_issue.schema";

			URL source = new URI(schemaUrl).toURL();
			RuleFactory ruleFactory = new RuleFactory(new DefaultGenerationConfig() {
				@Override
				public SourceType getSourceType() {
					return SourceType.JSON;
				}

				@Override
				public char[] getPropertyWordDelimiters() {
					return new char[] { '-', ' ', '_' };
				}

			}, new Jackson2Annotator(), new SchemaStore());
			SchemaGenerator schemaGenerator = new SchemaGenerator();
			SchemaMapper schemaMapper = new SchemaMapper(ruleFactory, schemaGenerator);
			schemaMapper.generate(codeModel, "ClassName", "com.example.jira", source);
			codeModel.build(new File(f.getCanonicalPath() + "/src/test/java/"));
		} catch (IOException e) {
			fail(e.getMessage());
		} catch (URISyntaxException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}

	}
}
