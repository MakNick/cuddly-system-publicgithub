package com.topicus.CFPApplication.domain;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import org.springframework.data.annotation.CreatedDate;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Entity
@ApiModel(value = "PresentationDraft", description = "Holds all values for the presentationdrafts")
public class PresentationDraft {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty(position = 1, required = true, value = "The unique identifier for the presentationdraft", hidden = true)
	private long id;

	@ApiModelProperty(position = 2, required = true, value = "What will the presentation be about")
	private String subject;
	@ApiModelProperty(position = 3, value = "Conference has a list of categories. One of those may be assigned here")
	private String category;

	@Lob
	@Column(name = "summary", length = 512)
	@ApiModelProperty(position = 4, required = true, value = "Applicant's short explanation about his/her idea")
	private String summary;
	@ApiModelProperty(position = 5, value = "Type of presentation. Example: workshop")
	private String type;
	@ApiModelProperty(position = 6, value = "How much time the applicant needs to host his/her presentation")
	private int duration;

	@CreatedDate
	@ApiModelProperty(position = 7, example = "2018-05-23T01:20:30")
	private LocalDateTime timeOfCreation = LocalDateTime.now();

	@ApiModel(value = "Label", description = "Labels that can be assigned to presentation drafts")
	public enum Label {
		UNLABELED, DENIED, ACCEPTED, RESERVED, UNDETERMINED
	}

	@Enumerated(EnumType.STRING)
	@ApiModelProperty(position = 8, value = "The label of this presentationdraft. If no label is given, the default label will be: UNLABELED", hidden = true)
	private Label label = Label.UNLABELED;

	@ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "Applicant_PresentationDraft", joinColumns = {
			@JoinColumn(name = "presentationDraft_id") }, inverseJoinColumns = { @JoinColumn(name = "applicant_id") })
	@ApiModelProperty(position = 9, required = true, value = "This list hold the hosts of this presentationdraft")
	private Set<Applicant> applicants = new HashSet<Applicant>();

	@ManyToOne
	@ApiModelProperty(position = 10, required = true, value = "Conference to which this presentationdraft belongs")
	private Conference conference;

	public void addApplicant(Applicant applicant) {
		this.applicants.add(applicant);
	}

	// Getters en Setters:
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public LocalDateTime getTimeOfCreation() {
		return timeOfCreation;
	}

	public void setTimeOfCreation(LocalDateTime timeOfCreation) {
		this.timeOfCreation = timeOfCreation;
	}

	public Enum<Label> getLabel() {
		return label;
	}

	public void setLabel(Label label) {
		this.label = label;
	}

	public Set<Applicant> getApplicants() {
		return applicants;
	}

	public void setApplicants(Set<Applicant> applicants) {
		this.applicants = applicants;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	@ApiModelProperty(hidden = true)
	public void setConference(Conference conference) {
		this.conference = conference;
	}
	
	@ApiModelProperty(hidden = true)
	public Conference getConference() {
		return conference;
	}
}
