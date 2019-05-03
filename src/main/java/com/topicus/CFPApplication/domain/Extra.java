package com.topicus.CFPApplication.domain;

import java.time.Period;

import javax.persistence.Entity;
import javax.persistence.Id;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Entity
@ApiModel(value = "Extra", description = "Any extra facilities that will be available for the conference")
public class Extra {

	@Id
	@ApiModelProperty(position = 1, value = "Unique identifier for the extra facility")
	private String name;

	@ApiModelProperty(position = 2, value = "The description of the extra facility")
	private String description;

	@ApiModelProperty(position = 3, value = "How long the extra facility will by available for the duration of the conference", hidden = true)
	private Period duration;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Period getDuration() {
		return duration;
	}

	public void setDuration(Period duration) {
		this.duration = duration;
	}
}
