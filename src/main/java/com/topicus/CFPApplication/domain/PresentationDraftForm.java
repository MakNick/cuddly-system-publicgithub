package com.topicus.CFPApplication.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Entity
@ApiModel(value = "PresentationDraftForm", description = "Holds extra values to be added to the form")
public class PresentationDraftForm {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty(position = 1, required = true, value = "The unique identifier for the presentationdraft", hidden = true)
	private int id;
	
	@ApiModelProperty(position = 2, value = "First optional extra field for the from")
	private String optionalOne;
	
	@ApiModelProperty(position = 3, value = "Second optional extra field for the from")
	private String optionalTwo;
	
	@ApiModelProperty(position = 4, value = "Last optional extra field for the from")
	private String optionalThree;
	
	@OneToOne
	@ApiModelProperty(position = 5, value = "Conference to which this form belongs")
	private Conference conference;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getOptionalOne() {
		return optionalOne;
	}

	public void setOptionalOne(String optionalOne) {
		this.optionalOne = optionalOne;
	}

	public String getOptionalTwo() {
		return optionalTwo;
	}

	public void setOptionalTwo(String optionalTwo) {
		this.optionalTwo = optionalTwo;
	}

	public String getOptionalThree() {
		return optionalThree;
	}

	public void setOptionalThree(String optionalThree) {
		this.optionalThree = optionalThree;
	}

}
