package com.topicus.CFPApplication.domain;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Entity
@JsonIgnoreProperties(value = "presentationDrafts")
@ApiModel(value = "Applicant", description = "Holds all values for the applicant object")
public class Applicant {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty(value = "The unique identifier for the applicant", required = true)
	private long id;

	@ApiModelProperty(required = true)
	private String name;
	@ApiModelProperty(required = true)
	private String email;
	private String phonenumber;
	@ApiModelProperty(value = "Current job, hobby or interest of the applicant")
	private String occupation;
	private String gender;
	@ApiModelProperty(required = true)
	private LocalDate dateOfBirth;
	@ApiModelProperty(value = "Attributes the applicant needs to host his/her presentation(s)")
	private String requests;

	@ManyToMany(mappedBy = "applicants", fetch = FetchType.EAGER)
	@ApiModelProperty(value = "List of presentationDrafts that this applicant wants to host", required = true)
	private Set<PresentationDraft> presentationDrafts = new HashSet<PresentationDraft>();

	@ManyToMany(mappedBy = "applicants", fetch = FetchType.EAGER)
	@ApiModelProperty(value = "List of finalized presentations that this applicant will host", required = true)
	private Set<Presentation> presentations = new HashSet<Presentation>();

	public void addPresentationDraft(PresentationDraft presentationDraft) {
		this.presentationDrafts.add(presentationDraft);
	}

	public void addPresentation(Presentation presentation) {
//		System.out.println("check");
		this.presentations.add(presentation);
	}

	// Getters en Setters:
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhonenumber() {
		return phonenumber;
	}

	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}

	public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getRequests() {
		return requests;
	}

	public void setRequests(String requests) {
		this.requests = requests;
	}

	public Set<PresentationDraft> getPresentationDrafts() {
		return presentationDrafts;
	}

	public void setPresentationDrafts(Set<PresentationDraft> presentationDrafts) {
		this.presentationDrafts = presentationDrafts;
	}
	
	public Set<Presentation> getPresentations() {
		return presentations;
	}

	public void setPresentations(Set<Presentation> presentations) {
		this.presentations = presentations;
	}

}
