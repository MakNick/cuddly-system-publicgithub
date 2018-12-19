package com.topicus.CFPApplication.persistence;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.ws.rs.core.Response;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.topicus.CFPApplication.domain.Applicant;
import com.topicus.CFPApplication.domain.PresentationDraft;
import com.topicus.CFPApplication.domain.PresentationDraft.Label;


@RunWith(MockitoJUnitRunner.class)
public class PresentationDraftServiceTest {
	
	@Mock
	PresentationDraftRepository draftRepo;
	
	@InjectMocks
	PresentationDraftService draftService;
	
	@Mock
	ApplicantService applicantService;
	
	@Mock
	ApplicantRepository applicantRepo;
	
	@Test
	public void makePresentationDraftFinalAcceptedTest() {
		List<PresentationDraft> listUnlabeled = new ArrayList<>();
		PresentationDraft pd1 = new PresentationDraft();
		pd1.setLabel(PresentationDraft.Label.UNLABELED);
		listUnlabeled.add(pd1);
		
		Mockito.when(this.draftRepo.findPresentationDraftByLabel(Label.UNLABELED)).thenReturn(listUnlabeled);
		
		Response result = draftService.makePresentationDraftsFinal();
		
		Mockito.verify(this.draftRepo).findPresentationDraftByLabel(Label.UNLABELED);
		
		Assert.assertEquals(412, result.getStatus());
	}
	
	@Test
	public void makePresentationDraftFinalUndeterminedTest() {
		List<PresentationDraft> listUndetermined = new ArrayList<>();
		PresentationDraft presUndetermined = new PresentationDraft();
		presUndetermined.setLabel(Label.UNDETERMINED);
		listUndetermined.add(presUndetermined);

		Mockito.when(this.draftRepo.findPresentationDraftByLabel(Label.UNDETERMINED)).thenReturn(listUndetermined);
		
		int response412 = draftService.makePresentationDraftsFinal().getStatus();
		
		Mockito.verify(this.draftRepo).findPresentationDraftByLabel(Label.UNDETERMINED);
		
		Assert.assertEquals(412, response412);
	}
	
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
		PresentationDraft resultPres = this.draftService.linkPresentationDraftWithApplicants(pres, appliList);
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
		PresentationDraft resultPres = this.draftService.linkPresentationDraftWithApplicants(pres, appliList);
		// assert
		Mockito.verify(this.applicantRepo).findApplicantByNameAndEmail("test", "email");
		
		Assert.assertEquals("test", resultPres.getCategory());
		
	}
	
	@Test
	public void findByLabelTest() {
		//arrange 
		PresentationDraft pres = new PresentationDraft();
		pres.setLabel(PresentationDraft.Label.ACCEPTED);
		List<PresentationDraft> list = new ArrayList<>();
		list.add(pres);
		
		Mockito.when(this.draftRepo.findPresentationDraftByLabel(Label.ACCEPTED)).thenReturn(list);
		// act
		List<PresentationDraft> testList = (List<PresentationDraft>)this.draftService.findByLabel(2);
		// assert
		Mockito.verify(this.draftRepo).findPresentationDraftByLabel(Label.ACCEPTED);
		
		Assert.assertEquals(1, testList.size());
	}
	
	@Test
	public void changeLabelTestElse() {
		PresentationDraft pres = new PresentationDraft();
		pres.setLabel(Label.ACCEPTED);
		Optional<PresentationDraft> opt = Optional.of(pres);
		
		Mockito.when(this.draftRepo.findById(Mockito.anyLong())).thenReturn(opt);
	
		boolean result = this.draftService.changeLabel(Mockito.anyLong(), 2);
		
		Assert.assertEquals(false, result);
	}
	
	@Test
	public void changeLabelTest() {
		Optional<PresentationDraft> opt = Optional.of(new PresentationDraft());
		
		Mockito.when(this.draftRepo.findById(Mockito.anyLong())).thenReturn(opt);
	
		boolean result = this.draftService.changeLabel(Mockito.anyLong(), 1);
		boolean resultDefault = this.draftService.changeLabel(Mockito.anyLong(), 5);
		
		Assert.assertEquals(true, result);
		Assert.assertEquals(false, resultDefault);
		
	}
	
	@Test
	public void findByIdTest() {
		Optional<PresentationDraft> opt = Optional.of(new PresentationDraft());
		opt.get().setId(1);
		
		Mockito.when(this.draftRepo.findById((long)1)).thenReturn(opt);
	
		PresentationDraft testItem = this.draftService.findById((long)1).get();
		
		Mockito.verify(this.draftRepo).findById((long)1);
		
		Assert.assertEquals(1, testItem.getId());
		
	}

	@Test
	public void saveTest() {
		PresentationDraft pres = new PresentationDraft();
		
		Mockito.when(this.draftRepo.save(pres)).thenReturn(pres);
	
		PresentationDraft testItem = this.draftService.save(pres);
		
		Mockito.verify(this.draftRepo).save(Mockito.any(PresentationDraft.class));
		
		Assert.assertEquals(pres, testItem);
		
	}
	
	@Test
	public void findByCategoryTest() {
		PresentationDraft pres = new PresentationDraft();
		pres.setCategory("test");
		
		List<PresentationDraft> list = new ArrayList<>();
		list.add(pres);
		
		Mockito.when(this.draftRepo.findPresentationDraftByCategory("test")).thenReturn(list);
		
		List<PresentationDraft> testList = (List<PresentationDraft>)this.draftService.findByCategory("test");
		
		Mockito.verify(this.draftRepo).findPresentationDraftByCategory(Mockito.anyString());
		
		Assert.assertEquals(1, testList.size());
		
	}
	
	@Test
	public void findAllTest() {
		//arrange
		PresentationDraft pres = new PresentationDraft();
		List<PresentationDraft> list = new ArrayList<>();
		list.add(pres);
		
		Mockito.when(this.draftRepo.findAll()).thenReturn(list);
		//act
		List<PresentationDraft> testList = (List<PresentationDraft>)this.draftService.findAll();
		//assert
		Mockito.verify(this.draftRepo).findAll();
		
		Assert.assertEquals(1, testList.size());
		
		
	}

}
