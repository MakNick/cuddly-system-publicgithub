package com.topicus.CFPApplication.domain;

import java.util.HashSet;
import java.util.Set;

public class PresentationDraftApplicant {
	private PresentationDraft presentationDraft;
	private Set<Applicant> applicants = new HashSet<Applicant>(); 
	
	public PresentationDraft getPresentationDraft() {
		return presentationDraft;
	}
	public void setPresentationDraft(PresentationDraft presentationDraft) {
		this.presentationDraft = presentationDraft;
	}
	public void addApplicant(Applicant applicant) {
		this.applicants.add(applicant);
	}
	public Set<Applicant> getApplicants() {
		return applicants;
	}
}
