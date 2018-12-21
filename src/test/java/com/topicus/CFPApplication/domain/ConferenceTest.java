package com.topicus.CFPApplication.domain;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ConferenceTest {

	@Test
	public void addPresentationDraftTest() {
		Conference c = new Conference();
		PresentationDraft pd = new PresentationDraft();
		c.addPresentationDraft(pd);
		
		Assert.assertEquals(1, c.getPresentationDrafts().size());
	}

}
