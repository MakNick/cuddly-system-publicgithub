package com.topicus.CFPApplication.domain;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import org.springframework.beans.factory.annotation.Autowired;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Entity
@ApiModel(value = "Conference", description = "Holds all values for the conference object")
public class Conference {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty(value = "The unique identifier for the conference", required = true)
	private long id;

	@ApiModelProperty(required = true)
	private String name;

	private LocalDateTime startDate;
	@ApiModelProperty(required = true)
	private LocalDateTime endDate;
	@ApiModelProperty(value = "After this day the organizor can make a definitive selection of all presentations, and presentations can no longer be submitted", required = true)
	private LocalDateTime deadlinePresentationDraft;

	@Autowired
	@Column(name = "category")
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "categories", joinColumns = @JoinColumn(name = "conference_id"))
	@ApiModelProperty(value = "Categories that can be assigned to presentations will be added here")
	private Set<String> categories;

	@OneToMany(fetch = FetchType.EAGER)
	@JoinColumn(name = "conference_id")
	@ApiModelProperty(value = "All presentations from this conference will be added to this list")
	private Set<PresentationDraft> presentationDrafts = new HashSet<PresentationDraft>();

	public void addPresentationDraft(PresentationDraft presentationDraft) {
		this.presentationDrafts.add(presentationDraft);
		presentationDraft.setConference(this);
	}

	// Getters en Setters:
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

	public LocalDateTime getdeadlinePresentationDraft() {
		return deadlinePresentationDraft;
	}

	public void setdeadlinePresentationDraft(LocalDateTime deadlinePresentationDraft) {
		this.deadlinePresentationDraft = deadlinePresentationDraft;
	}
}
