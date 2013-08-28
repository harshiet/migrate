package com.kaanha.migrate.core;

import static org.junit.Assert.fail;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.junit.BeforeClass;
import org.junit.Test;

import com.kaanha.migrate.core.persistence.domain.ArtifactType;
import com.kaanha.migrate.core.persistence.domain.AttributeType;
import com.kaanha.migrate.core.persistence.domain.SystemAttribute;
import com.kaanha.migrate.core.persistence.domain.SystemX;

public class PersistenceTest {

	private static EntityManager em;

	@BeforeClass
	public static void setupEm() {
		try {
			em = Persistence.createEntityManagerFactory("migrate_test").createEntityManager();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testSystemArtifact() {
		em.getTransaction().begin();

		SystemX systemRally = new SystemX("Rally", "Community");
		systemRally.addAttribute(ArtifactType.SUBSCRIPTION, AttributeType.NAME, "Subscription");
		systemRally.addAttribute(ArtifactType.SUBSCRIPTION, AttributeType.UNMAPPED, "Workspaces");
		systemRally.addAttribute(ArtifactType.WORKSAPCE, AttributeType.NAME, "Workspace");
		systemRally.addAttribute(ArtifactType.PROJECT, AttributeType.NAME, "name");
		systemRally.addAttribute(ArtifactType.PROJECT, AttributeType.DESCRIPTION, "description");
		systemRally.addAttribute(ArtifactType.PROJECT, AttributeType.UNMAPPED, "ObjectID");
		systemRally.addAttribute(ArtifactType.USER_STORY, AttributeType.NAME, "hierarchicalrequirement");
		systemRally.addAttribute(ArtifactType.TYPE_DEFINITION, AttributeType.NAME, "typedefinition");
		systemRally.addAttribute(ArtifactType.TYPE_DEFINITION, AttributeType.UNMAPPED, "Attributes");
		systemRally.addAttribute(ArtifactType.TYPE_DEFINITION, AttributeType.DISPLAY_NAME, "DisplayName");

		SystemX systemJira = new SystemX("Jira", "OnDemand");
		systemRally.addAttribute(ArtifactType.PROJECT, AttributeType.NAME, "title");
		systemRally.addAttribute(ArtifactType.PROJECT, AttributeType.DESCRIPTION, "desc");

		em.persist(systemRally);
		em.persist(systemJira);
		em.getTransaction().commit();
	}

	@Test
	public void testSearch() {
		Query q = em
				.createQuery("select s from com.kaanha.migrate.core.persistence.domain.SystemX s where s.name = :name");
		q.setParameter("name", "Rally");
		try {
			SystemX system = (SystemX) q.getSingleResult();
			assert (system.getAttributes().size() > 0);
			for (SystemAttribute attribute : system.getAttributes()) {
				System.out.println(attribute);
			}
		} catch (NoResultException nre) {
			fail(nre.getMessage());
		}

	}

}
