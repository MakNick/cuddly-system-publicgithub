package com.topicus.CFPApplication.domain;

import java.util.HashSet;
import java.util.Set;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "PresentationDraftConference", description = "Holds the conferences and presentationdrafts")
public class PresentationDraftConference {
	@ApiModelProperty(position = 1, required = true, value = "The conference that was created")
	private Conference conference;

	@ApiModelProperty(position = 2, required = true, value = "The presentationdrafts of the conference", hidden = true)
	private Set<PresentationDraft> presentationdrafts = new HashSet<PresentationDraft>();

	public Conference getConference() {
		return conference;
	}

	public void setConference(Conference conference) {
		this.conference = conference;
	}

	public void addPresentationDraft(PresentationDraft presentationDraft) {
		this.presentationdrafts.add(presentationDraft);
	}

	public Set<PresentationDraft> getPresentationDrafts() {
		return presentationdrafts;
	}
}
