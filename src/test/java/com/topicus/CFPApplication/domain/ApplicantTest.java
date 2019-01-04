package com.topicus.CFPApplication.domain;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ApplicantTest {

	@Test
	public void addPresentationDraftTest() {
		PresentationDraft pd = new PresentationDraft();
		Applicant a = new Applicant();
		a.addPresentationDraft(pd);

		Assert.assertEquals(1, a.getPresentationDrafts().size());
	}

	@Test
	public void addPresentationTest() {
		Presentation p = new Presentation();
		Applicant a = new Applicant();
		a.addPresentation(p);

		Assert.assertEquals(1, a.getPresentations().size());
	}

}
