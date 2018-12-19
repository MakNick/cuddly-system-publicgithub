package com.topicus.CFPApplication.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;

@Entity
public class Presentation {

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

	@ManyToMany(fetch=FetchType.EAGER, cascade = 
        {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinTable(
	        name = "Applicant_Presentation", 
	        joinColumns = { @JoinColumn(name = "presentation_id") }, 
	        inverseJoinColumns = { @JoinColumn(name = "applicant_id") }
	    )
	private Set<Applicant> applicants = new HashSet<Applicant>();

	public void addApplicant(Applicant applicant) {
		this.applicants.add(applicant);
	}

	public long getId() {
		return id;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
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

//	public Set<Applicant> getApplicants() { return applicants; }
//	 
//	public void setApplicants(Set<Applicant> applicants) { 
//		 this.applicants = applicants; }
//	 
	 
}
