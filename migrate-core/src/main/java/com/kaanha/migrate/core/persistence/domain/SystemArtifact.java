package com.kaanha.migrate.core.persistence.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "system_artifact")
public class SystemArtifact extends Persistable {

	@Enumerated(EnumType.STRING)
	ArtifactType artifactType;

	@Column(columnDefinition = "TEXT")
	String name;

	@ManyToOne
	@JoinColumn(name = "system_id", referencedColumnName = "ID")
	SystemX system;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "artifact")
	List<SystemArtifactAttribute> attributes = new ArrayList<SystemArtifactAttribute>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Transient
	public SystemX getSystem() {
		return system;
	}

	public void setSystem(SystemX system) {
		this.system = system;
	}

	public ArtifactType getArtifactType() {
		return artifactType;
	}

	public void setArtifactType(ArtifactType artifactType) {
		this.artifactType = artifactType;
	}

	public List<SystemArtifactAttribute> getAttributes() {
		return attributes;
	}

	public List<String> getAttributeNames() {
		List<String> attributeNames = new ArrayList<String>();
		for (SystemArtifactAttribute systemArtifactAttribute : getAttributes()) {
			attributeNames.add(systemArtifactAttribute.getName());
		}
		return attributeNames;
	}

	public SystemArtifactAttribute addAttribute(String name) {
		SystemArtifactAttribute attribute = new SystemArtifactAttribute(name);
		attribute.setArtifact(this);
		attributes.add(attribute);
		return attribute;
	}

	public void addAttributes(String... attributes) {
		for (String attribute : attributes) {
			addAttribute(attribute);
		}
	}
}