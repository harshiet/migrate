package com.kaanha.migrate.core.persistence.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "system_artifact_attribute")
public class SystemArtifactAttribute extends Persistable {

	@Column(columnDefinition = "TEXT")
	String name;

	boolean hasChild = false;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "source", referencedColumnName = "ID")
	List<AttributeMapping> attributeMappings = new ArrayList<AttributeMapping>();

	@ManyToOne
	@JoinColumn(name = "system_artifact_id", referencedColumnName = "ID")
	private SystemArtifact artifact;

	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "child_attribute", referencedColumnName = "ID")
	SystemArtifactAttribute childAttribute;

	public SystemArtifactAttribute() {
		super();
	}

	public SystemArtifactAttribute(String name) {
		super();
		setName(name);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<AttributeMapping> getAttributeMappings() {
		return attributeMappings;
	}

	public void addMapping(SystemArtifactAttribute attribute) {
		AttributeMapping attributeMapping = new AttributeMapping(attribute);
		attributeMappings.add(attributeMapping);
		AttributeMapping attributeMappingReverse = new AttributeMapping(this);
		attribute.getAttributeMappings().add(attributeMappingReverse);
	}

	public SystemArtifact getArtifact() {
		return artifact;
	}

	public void setArtifact(SystemArtifact artifact) {
		this.artifact = artifact;
	}

	public String getAttributeMapping(SystemX source) {
		for (AttributeMapping attributeMapping : getAttributeMappings()) {
			if (attributeMapping.getMappedAttribute().getArtifact().getSystem().equals(source)) {
				return attributeMapping.getMappedAttribute().getName();
			}
		}
		return null;
	}

	public SystemArtifactAttribute withChildAttribute(String childAttributeName) {
		SystemArtifactAttribute childAttribute = new SystemArtifactAttribute(childAttributeName);
		this.childAttribute = childAttribute;
		this.hasChild = true;
		return this;
	}

	public boolean hasChild() {
		return hasChild;
	}

	public SystemArtifactAttribute getChildAttribute() {
		return childAttribute;
	}

}