package com.kaanha.migrate.core;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.junit.BeforeClass;
import org.junit.Test;

import com.kaanha.migrate.core.persistence.domain.ArtifactType;
import com.kaanha.migrate.core.persistence.domain.AttributeMapping;
import com.kaanha.migrate.core.persistence.domain.SystemArtifact;
import com.kaanha.migrate.core.persistence.domain.SystemArtifactAttribute;
import com.kaanha.migrate.core.persistence.domain.SystemX;

public class PersistenceTest {

	private static EntityManager em;

	@BeforeClass
	public static void setupEm() {
		em = Persistence.createEntityManagerFactory("migrate_test").createEntityManager();
	}

	@Test
	public void testSystemArtifact() {
		em.getTransaction().begin();

		SystemX systemRally = new SystemX("Rally", "Community");
		SystemArtifact rallySubscription = systemRally.addArtifact(ArtifactType.SUBSCRIPTION, "subscription");
		SystemArtifact rallyWorkspace = systemRally.addArtifact(ArtifactType.WORKSAPCE, "workspace");
		SystemArtifact rallyProject = systemRally.addArtifact(ArtifactType.PROJECT, "project");
		SystemArtifact rallyUserStory = systemRally.addArtifact(ArtifactType.USER_STORY, "HierarchicalRequirement");
		rallySubscription.addAttributes("Workspaces");
		rallyWorkspace.addAttributes("Projects");

		SystemArtifactAttribute rallyProjectName = rallyProject.addAttribute("Name");
		SystemArtifactAttribute rallyProjectOwner = rallyProject.addAttribute("Owner").withChildAttribute("_refObjectName");
		rallyProject.addAttribute("objectid");
		rallyProject.addAttribute("iterations");

		rallyUserStory.addAttribute("attachments");
		rallyUserStory.addAttribute("iteration");
		rallyUserStory.addAttribute("tasks");
		rallyUserStory.addAttribute("defects");
		
		SystemX systemJira = new SystemX("Jira", "OnDemand");
		SystemArtifact jiraProjectArtifact = systemJira.addArtifact(ArtifactType.PROJECT, "Project");
		SystemArtifactAttribute jiraProjectName = jiraProjectArtifact.addAttribute("title");
		SystemArtifactAttribute JiraProjectOwner = jiraProjectArtifact.addAttribute("admin");

		rallyProjectName.addMapping(jiraProjectName);
		rallyProjectOwner.addMapping(JiraProjectOwner);

		em.persist(systemRally);
		em.persist(systemJira);
		em.getTransaction().commit();
	}

	@Test
	public void testSearch() {
		Query q = em.createQuery("select s from com.kaanha.migrate.core.persistence.domain.SystemX s where s.name = :name");
		q.setParameter("name", "Rally");
		try {
			SystemX system = (SystemX) q.getSingleResult();
			assertTrue(system.getArtifacts().size() > 0);
			for (SystemArtifact artifact : system.getArtifacts()) {
				System.out.println(artifact.getName());
				for (SystemArtifactAttribute attribute : artifact.getAttributes()) {
					System.out.println(attribute.getName());
					for (AttributeMapping mapping : attribute.getAttributeMappings()) {
						System.out.println(mapping.getMappedAttribute().getArtifact().getSystem().getName() + " - " + mapping.getMappedAttribute().getName());
					}
				}

			}
		} catch (NoResultException nre) {
			fail(nre.getMessage());
		}

	}

}
