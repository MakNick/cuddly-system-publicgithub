package com.topicus.CFPApplication.domain.conference;

import java.sql.Blob;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.topicus.CFPApplication.domain.conference.Stage;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Entity
@ApiModel(value = "Attribute", description = "Holds attribute values for the stage")
public class Attribute {

	@Id
	@ApiModelProperty(position = 1, value = "Unique identifier for the attribute")
	private String name;

	@ApiModelProperty(position = 2, value = "Amount of the item needed")
	private int amount;

	// TODO: onderzoek hoe dit werkt 
	@ApiModelProperty(position = 3, value = "The image for this attribute", hidden = true)
	private Blob icon;

	@ManyToOne
	@ApiModelProperty(position = 4, value = "The stage to which this attribute belongs", hidden = true)
	private Stage stage;

	public Blob getIcon() {
		return icon;
	}

	public Stage getStage() {
		return stage;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	public void setIcon(Blob icon) {
		this.icon = icon;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
