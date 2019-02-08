package com.topicus.CFPApplication.domain.conference;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import org.springframework.beans.factory.annotation.Autowired;

import com.topicus.CFPApplication.domain.conference.Attribute;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Entity
@ApiModel(value = "Stage", description = "Holds name and attributes for a stage")
public class Stage {

	@Id
	@ApiModelProperty(position = 1, value = "Unique identifier for the stage")
	private String name;

	@Autowired
	@Column(name = "attribute")
	@OneToMany(fetch = FetchType.EAGER)
	@JoinColumn(name = "stage_name")
	@ApiModelProperty(position = 2, value = "Attributes of the stage will be added here")
	private Set<Attribute> attributes;

	public Stage(String name) {
		this.name = name;
	}

	public Set<Attribute> getAttributes() {
		return attributes;
	}

	public void setAttributes(Set<Attribute> attributes) {
		this.attributes = attributes;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
