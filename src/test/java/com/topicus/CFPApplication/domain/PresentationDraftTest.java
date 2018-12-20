package com.topicus.CFPApplication.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.Assert;
import org.junit.Test;

import com.topicus.CFPApplication.domain.PresentationDraft.Label;

public class PresentationDraftTest {
	
	PresentationDraft presentationDraft = new PresentationDraft();
	
	@Test
	public void setSubjectTest() {
		presentationDraft.setSubject("test");
		Assert.assertEquals("test", presentationDraft.getSubject());
	}
	
	@Test
	public void setSummaryTest() {
		presentationDraft.setSummary("summary presentationdraft");
		Assert.assertEquals("summary presentationdraft", presentationDraft.getSummary());
	}
	
	@Test
	public void setTypeTest() {
		presentationDraft.setType("workshop");
		Assert.assertEquals("workshop", presentationDraft.getType());
	}
	
	@Test
	public void setDurationTest() {
		presentationDraft.setDuration(45);
		Assert.assertEquals(45, presentationDraft.getDuration());
	}
	
	@Test
	public void setTimeOfCreation() {
		LocalDateTime ldt = LocalDateTime.now();
		presentationDraft.setTimeOfCreation(ldt);
		Assert.assertEquals(ldt, presentationDraft.getTimeOfCreation());
	}
	
	@Test
	public void setLabelTest() {
		presentationDraft.setLabel(Label.ACCEPTED);
		Assert.assertEquals(Label.ACCEPTED, presentationDraft.getLabel());
	}
	
	@Test
	public void setCategoryTest() {
		presentationDraft.setCategory("category 1");
		Assert.assertEquals("category 1", presentationDraft.getCategory());
	}
	
	@Test
	public void setApplicantTest() {
		Applicant applicant = new Applicant();
		presentationDraft.addApplicant(applicant);
		Assert.assertEquals(1, presentationDraft.getApplicants().size());
		Assert.assertTrue(presentationDraft.getApplicants().contains(applicant));
	}
}
