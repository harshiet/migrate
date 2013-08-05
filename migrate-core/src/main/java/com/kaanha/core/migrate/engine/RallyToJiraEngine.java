package com.kaanha.core.migrate.engine;

import java.io.IOException;
import java.net.URISyntaxException;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import org.springframework.web.client.RestClientException;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.kaanha.migrate.core.api.read.RallyReadApi;
import com.kaanha.migrate.core.api.write.JiraWriteApi;
import com.kaanha.migrate.core.persistence.api.DBRepository;
import com.kaanha.migrate.core.persistence.domain.ArtifactType;
import com.kaanha.migrate.core.persistence.domain.SystemX;

public class RallyToJiraEngine {
	DBRepository dbRepository;
	SystemX source, target;
	RallyToJiraTransformationEngine transform;

	public RallyToJiraEngine() {
		EntityManager em = Persistence.createEntityManagerFactory("migrate").createEntityManager();
		dbRepository = new DBRepository(em);
		source = dbRepository.findSystemByName("Rally");
		target = dbRepository.findSystemByName("Jira");
		transform = new RallyToJiraTransformationEngine(source, target);
	}

	public void migrate() throws RestClientException, URISyntaxException, IOException {
		RallyReadApi rally = new RallyReadApi("https://rally1.rallydev.com/slm/webservice/v2.0/", "rally.user.2@gmail.com", "RallyUser123!", dbRepository);
		JiraWriteApi jira = new JiraWriteApi("https://agiletool.executiveboard.com", "hagarwal", "1234");

		JsonArray subscriptions = rally.getSubscriptions();
		System.out.println(subscriptions);
		for (JsonElement subscription : subscriptions) {
			JsonArray workspaces = rally.getSubscriptionWorkspaces(subscription);
			System.out.println(workspaces);
			for (JsonElement workspaceElement : workspaces) {
				System.out.println(workspaceElement);
				JsonArray projects = rally.getWorkspaceProjects(workspaceElement);
				System.out.println(projects);
				for (JsonElement projectElement : projects) {
					JsonObject project = rally.getProjectDetails(projectElement);
					System.out.println(project);
					// create project
					JsonObject jiraProject = transform.transform(projectElement.getAsJsonObject(), ArtifactType.PROJECT);
					System.out.println(jiraProject);
					jiraProject = jira.createProject(jiraProject);
					JsonArray userStories = rally.getParentLevelUserStories(project);
					System.out.println(userStories);
					for (JsonElement userStoryElement : userStories) {
						System.out.println(userStoryElement);
						// Create issue in JIRA
						JsonObject jiraIssue = transform.transform(userStoryElement.getAsJsonObject(), ArtifactType.USER_STORY);
						jiraIssue = jira.createIssue(jiraIssue);
						JsonArray defects = rally.getUserStoryDefects(userStoryElement);
						System.out.println(defects);
						JsonArray tasks = rally.getUserStoryTasks(userStoryElement);
						System.out.println(tasks);
						JsonArray attachments = rally.getUserStoryAttachments(userStoryElement);
						System.out.println(attachments);
					}

				}
			}

		}
		// for (Workspace workspace : subscription.getWorkspaces()) {
		// for (Project project : workspace.getProjects()) {
		//
		// }
		// }
	}

}