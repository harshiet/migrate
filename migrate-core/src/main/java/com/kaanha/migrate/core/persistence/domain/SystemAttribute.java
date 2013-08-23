package com.kaanha.migrate.core.persistence.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Entity
@Table(name = "system_attribute")
public class SystemAttribute extends Persistable {

	@Enumerated(EnumType.STRING)
	private ArtifactType artifact;

	@Enumerated(EnumType.STRING)
	private AttributeType attribute;

	@Column(columnDefinition = "TEXT")
	String name;

	public SystemAttribute(ArtifactType artifact, AttributeType attribute, String name) {
		super();
		this.artifact = artifact;
		this.attribute = attribute;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArtifactType getArtifact() {
		return artifact;
	}

	public void setArtifact(ArtifactType artifact) {
		this.artifact = artifact;
	}

	public AttributeType getAttribute() {
		return attribute;
	}

	public void setAttribute(AttributeType attribute) {
		this.attribute = attribute;
	}

	@Override
	public String toString() {
		return artifact + ":" + attribute + ":" + name;
	}

}