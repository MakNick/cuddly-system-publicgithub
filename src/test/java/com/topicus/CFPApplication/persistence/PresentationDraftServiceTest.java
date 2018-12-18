package com.topicus.CFPApplication.persistence;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ws.rs.core.Response;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.topicus.CFPApplication.domain.PresentationDraft;
import com.topicus.CFPApplication.domain.PresentationDraft.Label;


@RunWith(MockitoJUnitRunner.class)
public class PresentationDraftServiceTest {
	
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
	public void makePresentationDraftFinalAcceptedTest() {
		int responseOk = draftService.makePresentationDraftsFinal().getStatus();
		
		Assert.assertEquals(Response.ok().build().getStatus(), responseOk);
	}
	
	@Test
	public void makePresentationDraftFinalUndeterminedTest() {
		List<PresentationDraft> listUnlabeled = new ArrayList<>();
		PresentationDraft presUnlabeled = new PresentationDraft();
		presUnlabeled.setLabel(Label.UNDETERMINED);
		listUnlabeled.add(presUnlabeled);

		Mockito.when(this.draftRepo.findPresentationDraftByLabel(Label.UNDETERMINED)).thenReturn(listUnlabeled);
		
		int response412 = draftService.makePresentationDraftsFinal().getStatus();
		
		Mockito.verify(this.draftRepo).findPresentationDraftByLabel(Label.UNLABELED);
		Mockito.verify(this.draftRepo).findPresentationDraftByLabel(Label.UNDETERMINED);
		
		Assert.assertEquals(Response.status(412).build().getStatus(), response412);
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
