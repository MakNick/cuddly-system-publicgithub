package com.topicus.CFPApplication.persistence;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.topicus.CFPApplication.domain.Applicant;
import com.topicus.CFPApplication.domain.Conference;
import com.topicus.CFPApplication.domain.PresentationDraft;

@RunWith(MockitoJUnitRunner.class)
public class SubscribeServiceTest {

	@Mock
	PresentationDraftRepository draftRepo;
	
	@InjectMocks
	PresentationDraftService draftService;
	
	@InjectMocks
	SubscribeService subscribeService;
	
	@Mock
	ApplicantService applicantService;

	@Mock
	ApplicantRepository applicantRepo;
	
	@Test
	public void linkPresentationDraftWithApplicantsTestElse() { 
		// arrange
		PresentationDraft pres = new PresentationDraft();
		Applicant applElse = new Applicant();
		applElse.setName("testElse");
		applElse.setEmail("emailElse");
		Set<Applicant> appliList = new HashSet<>();
		appliList.add(applElse);
		Optional<Applicant> opt = Optional.ofNullable(null);
		
		Mockito.when(this.applicantRepo.findApplicantByNameAndEmail("testElse" ,"emailElse")).thenReturn(opt);
		// act
		PresentationDraft resultPres = this.subscribeService.linkPresentationDraftWithApplicants(pres, appliList);
		// assert
		Mockito.verify(this.applicantRepo).findApplicantByNameAndEmail("testElse", "emailElse");
		
		Assert.assertEquals("testElse", resultPres.getApplicants().iterator().next().getName());
		
	}
	
	@Test
	public void linkPresentationDraftWithApplicantsTest() { 
		// arrange
		PresentationDraft pres = new PresentationDraft();
		pres.setCategory("test");
		Applicant appl = new Applicant();
		appl.setName("test");
		appl.setEmail("email");
		Set<Applicant> appliList = new HashSet<>();
		appliList.add(appl);
		
		Optional<Applicant> opt = Optional.of(appl);
		
		Mockito.when(this.applicantRepo.findApplicantByNameAndEmail("test" ,"email")).thenReturn(opt);
		// act
		PresentationDraft resultPres = this.subscribeService.linkPresentationDraftWithApplicants(pres, appliList);
		// assert
		Mockito.verify(this.applicantRepo).findApplicantByNameAndEmail("test", "email");
		
		Assert.assertEquals("test", resultPres.getCategory());
		
	}
}
