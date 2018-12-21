package com.topicus.CFPApplication.domain;

import java.util.HashSet;
import java.util.Set;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "PresentationDraftApplicant", description = "Holds the host(s) of a presentation")
public class PresentationDraftApplicant {

	@ApiModelProperty(position = 1, value = "The presentation that was submitted", required = true)
	private PresentationDraft presentationDraft;

	@ApiModelProperty(position = 2, value = "The host(s) of the presentation", hidden = true, required = true)
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
