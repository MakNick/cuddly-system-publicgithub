package com.topicus.CFPApplication.domain;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import com.topicus.CFPApplication.constants.Gender;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Entity
@JsonIgnoreProperties( { "presentationDrafts", "presentations" } )
@ApiModel(value = "Applicant", description = "Holds all values for the applicant object")
public class Applicant {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty(position = 1, required = true, value = "The unique identifier for the applicant", hidden = true)
	private long id;

	@ApiModelProperty(position = 2, required = true)
	private String name;

	@ApiModelProperty(position = 3, required = true)
	private String email;

	@Column(length = 30)
	private String phoneNumber;

	@ApiModelProperty(position = 4, value = "Current job, hobby or interest of the applicant")
	private String occupation;

	private Gender gender; // mag deze eruit?

	@ApiModelProperty(position = 5, required = true, example = "1992-05-20")
	private LocalDate dateOfBirth;

	@ApiModelProperty(position = 6, value = "Attributes the applicant needs to host his/her presentation(s)")
	private String requests;

	@ManyToMany(mappedBy = "applicants", fetch = FetchType.EAGER)
	@ApiModelProperty(position = 7, value = "List of presentationDrafts that this applicant wants to host", hidden = true)
	private Set<PresentationDraft> presentationDrafts = new HashSet<PresentationDraft>();

	@ManyToMany(mappedBy = "applicants", fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@ApiModelProperty(position = 8, value = "List of finalized presentations that this applicant will host", hidden = true)
	private Set<Presentation> presentations = new HashSet<Presentation>();

	public void addPresentationDraft(PresentationDraft presentationDraft) {
		this.presentationDrafts.add(presentationDraft);
	}

	public void addPresentation(Presentation presentation) {
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

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
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
