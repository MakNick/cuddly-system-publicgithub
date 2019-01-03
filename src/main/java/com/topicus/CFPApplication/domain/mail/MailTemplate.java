package com.topicus.CFPApplication.domain.mail;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.topicus.CFPApplication.domain.PresentationDraft.Label;

@Entity
@Table(name = "mail_templates")
public class MailTemplate {
	
	@Id
	private Label label;
	
	private String subject;
	
	private String content;
	
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Label getLabel() {
		return label;
	}
	public void setLabel(Label label) {
		this.label = label;
	}

}
