package com.topicus.CFPApplication.domain;

import org.junit.Assert;
import org.junit.Test;

public class PresentationDraftApplicantTest {

	PresentationDraftApplicant presentationDraftApplicant = new PresentationDraftApplicant();

	@Test
	public void setPresentatoinDraftTest() {
		PresentationDraft presentationDraft = new PresentationDraft();
		presentationDraftApplicant.setPresentationDraft(presentationDraft);
		Assert.assertEquals(presentationDraft, presentationDraftApplicant.getPresentationDraft());
	}

	@Test
	public void addApplicantTest() {
		Applicant applicant = new Applicant();
		presentationDraftApplicant.addApplicant(applicant);

		Assert.assertTrue(presentationDraftApplicant.getApplicants().size() == 1);
	}

}
