package com.kaanha.migrate.core.persistence.domain;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "attribute_mapping")
public class AttributeMapping extends Persistable {

	@ManyToOne
	@JoinColumn(name = "target", referencedColumnName = "ID")
	SystemArtifactAttribute mappedAttribute;

	public AttributeMapping() {

	}

	public AttributeMapping(SystemArtifactAttribute attribute) {
		super();
		setMappedAttribute(attribute);
	}

	public SystemArtifactAttribute getMappedAttribute() {
		return mappedAttribute;
	}

	public void setMappedAttribute(SystemArtifactAttribute attribute) {
		this.mappedAttribute = attribute;
	}

}