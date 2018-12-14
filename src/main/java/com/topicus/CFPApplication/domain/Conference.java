package com.topicus.CFPApplication.domain;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

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

@Entity
public class Conference {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
    
	private String name;
	
	private LocalDateTime startDate;
	private LocalDateTime endDate;
	private LocalDateTime deadlinePresentationDraft;
	
	@ElementCollection(fetch= FetchType.EAGER)
	@CollectionTable(name = "categories", joinColumns = @JoinColumn(name="conference_id"))
	@Column(name="category")
	private Set<String> categories = new TreeSet<>((o1,o2) -> o1.toLowerCase().compareTo(o2.toLowerCase()));
	
	@OneToMany(fetch = FetchType.EAGER)
	@JoinColumn(name="conference_id" )
	private Set<PresentationDraft> presentationDrafts = new HashSet<PresentationDraft>();
	
	//Getters en Setters:
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
