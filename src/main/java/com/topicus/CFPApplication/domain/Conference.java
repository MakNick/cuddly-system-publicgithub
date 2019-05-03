package com.topicus.CFPApplication.domain;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
//import java.util.TreeSet;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.springframework.beans.factory.annotation.Autowired;

import com.topicus.CFPApplication.domain.Day;
import com.topicus.CFPApplication.domain.Presentation;
import com.topicus.CFPApplication.domain.PresentationDraft;
import com.topicus.CFPApplication.domain.PresentationDraftForm;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Entity
@ApiModel(value = "Conference", description = "Holds all value for the conference")
public class Conference {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty(position = 1, required = true, value = "The unique identifier for the conference", hidden = true)
	private long id;

	@ApiModelProperty(position = 2, required = true)
	private String name;

	@ApiModelProperty(position = 4, required = true, example = "2018-05-23T01:20:30")
	private LocalDateTime startDate;
	@ApiModelProperty(position = 5, required = true, example = "2018-05-23T01:20:30")
	private LocalDateTime endDate;
	@ApiModelProperty(position = 6, required = true, value = "After this day the organizor can make a definitive selection of all presentations, "
			+ "and presentations can no longer be submitted", example = "2018-05-23T01:20:30")
	private LocalDateTime deadlinePresentationDraft;

	@Autowired
	@Column(name = "category")
	@ElementCollection(fetch = FetchType.EAGER)
	@JoinColumn(name = "conference_id")
	@ApiModelProperty(position = 7, value = "Categories that can be assigned to presentations will be added here")
	private Set<String> categories;

//	@Autowired
//	@Column(name = "stage")
//	@OneToMany(fetch = FetchType.EAGER)
//	@JoinColumn(name = "conference_id")
//	@ApiModelProperty(position = 8, value = "Available stages for the presentations will be added here", hidden = true)
//	private Set<Stage> stages;

//	@Autowired
//	@Column(name = "extra")
//	@OneToMany(fetch = FetchType.EAGER)
//	@JoinColumn(name = "conference_id")
//	@ApiModelProperty(position = 9, value = "Extra facilities for the conference will be added here", hidden = true)
//	private Set<Extra> extras;

	@Column(name = "day")
	@OneToMany(fetch = FetchType.EAGER)
	@JoinColumn(name = "conference_id")
	@ApiModelProperty(position = 10, value = "Duration in days", hidden = true)
	private Set<Day> days = new HashSet<Day>();

//	@Column(name = "mail_template")
//	@OneToMany(fetch = FetchType.EAGER)
//	@JoinColumn(name = "conference_id")
//	@ApiModelProperty(position = 11, value = "Available e-mail templates", hidden = true)
//	private Set<MailTemplate> mailTemplates = new HashSet<MailTemplate>();

	@Column(name = "presentationdraft")
	@OneToMany(fetch = FetchType.EAGER)
	@JoinColumn(name = "conference_id")
	@ApiModelProperty(position = 12, value = "All presentations from this conference will be added to this list", hidden = true)
	private Set<PresentationDraft> presentationDrafts = new HashSet<PresentationDraft>();

	@Column(name = "presentation")
	@OneToMany(fetch = FetchType.EAGER)
	@JoinColumn(name = "conference_id")
	@ApiModelProperty(position = 12, value = "All presentations from this conference will be added to this list", hidden = true)
	private Set<Presentation> presentations = new HashSet<Presentation>();

	@OneToOne
	@ApiModelProperty(position = 13, value = "Form to be used for this conference", hidden = true)
	private PresentationDraftForm presentationDraftForm;

	public void addPresentationDraft(PresentationDraft presentationDraft) {
		this.presentationDrafts.add(presentationDraft);
	}

	public void addPresentation(Presentation presentation) {
		this.presentations.add(presentation);
	}

	// Getters en Setters:
//	public Set<Stage> getStages() {
//		return stages;
//	}

	public LocalDateTime getDeadlinePresentationDraft() {
		return deadlinePresentationDraft;
	}

	public void setDeadlinePresentationDraft(LocalDateTime deadlinePresentationDraft) {
		this.deadlinePresentationDraft = deadlinePresentationDraft;
	}

//	public Set<Extra> getExtras() {
//		return extras;
//	}
//
//	public void setExtras(Set<Extra> extras) {
//		this.extras = extras;
//	}

	public Set<Day> getDays() {
		return days;
	}

	public void setDays(Set<Day> days) {
		this.days = days;
	}

//	public void setStages(Set<Stage> stages) {
//		this.stages = stages;
//	}

	public Set<String> getCategories() {
		return categories;
	}

	public void setCategories(Set<String> categories) {
		this.categories = categories;
	}

	public LocalDateTime getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDateTime startDate) {
		this.startDate = startDate;
	}

	public LocalDateTime getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDateTime endDate) {
		this.endDate = endDate;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Set<PresentationDraft> getPresentationDrafts() {
		return presentationDrafts;
	}

	public void setPresentationDrafts(Set<PresentationDraft> presentationDrafts) {
		this.presentationDrafts = presentationDrafts;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public PresentationDraftForm getPresentationDraftForm() {
		return presentationDraftForm;
	}

	public void setPresentationDraftForm(PresentationDraftForm presentationDraftForm) {
		this.presentationDraftForm = presentationDraftForm;
	}

	public Set<Presentation> getPresentations() {
		return presentations;
	}

	public void setPresentations(Set<Presentation> presentations) {
		this.presentations = presentations;
	}
}
