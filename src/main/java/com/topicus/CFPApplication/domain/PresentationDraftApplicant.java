package com.topicus.CFPApplication.domain;

import java.util.HashSet;
import java.util.Set;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "PresentationDraftApplicant", description = "Holds the host(s) and presentationdraft")
public class PresentationDraftApplicant {

	@ApiModelProperty(position = 1, required = true, value = "The presentationdraft that was submitted")
	private PresentationDraft presentationDraft;

	@ApiModelProperty(position = 2, required = true, value = "The host(s) of the presentationdraft", hidden = true)
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
