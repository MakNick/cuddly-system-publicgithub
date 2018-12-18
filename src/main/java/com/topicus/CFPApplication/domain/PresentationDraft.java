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

@Entity
public class PresentationDraft {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private String subject;
	private String category;

	@Lob
	@Column(name = "summary", length = 512)
	private String summary;
	private String type;
	private int duration;

	@CreatedDate
	private LocalDateTime timeOfCreation = LocalDateTime.now();

	public enum Label {
		UNLABELED, DENIED, ACCEPTED, RESERVED, UNDETERMINED
	}

	@Enumerated(EnumType.STRING)
	private Label label = Label.UNLABELED;

	@ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "Applicant_PresentationDraft", joinColumns = {
			@JoinColumn(name = "presentationDraft_id") }, inverseJoinColumns = { @JoinColumn(name = "applicant_id") })
	private Set<Applicant> applicants = new HashSet<Applicant>();

	@ManyToOne
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

	public Enum getLabel() {
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

	public void setConference(Conference conference) {
		this.conference = conference;
	}
}
