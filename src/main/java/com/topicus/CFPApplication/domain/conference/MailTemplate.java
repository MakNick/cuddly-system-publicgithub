package com.topicus.CFPApplication.domain.conference;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.topicus.CFPApplication.domain.PresentationDraft.Label;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Entity
@ApiModel(value = "MailTemplate", description = "Holds the value to fill in an e-mail template")
public class MailTemplate {

	@Id
	@ApiModelProperty(position = 1, value = "Unique identifier for the template and type of the template")
	private Label label;

	@ApiModelProperty(position = 3, value = "Content of the mail")
	private String content;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Label getLabel() {
		return this.label;
	}

	public void setLabel(Label label) {
		this.label = label;
	}
}