package com.topicus.CFPApplication.persistence;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.naming.CannotProceedException;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.topicus.CFPApplication.domain.Conference;
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

	@Mock
	PresentationService presentationService;
	
	@Mock
	ConferenceRepository conferenceRepo;
	
	@InjectMocks
	ConferenceService conferenceService;

	@Test
	public void makePresentationDraftFinalUnlabeledTest() throws CannotProceedException, NoSuchElementException {
		Conference conf = new Conference();
		conf.setId(1);
		PresentationDraft pd1 = new PresentationDraft();
		pd1.setLabel(PresentationDraft.Label.UNLABELED);
		conf.addPresentationDraft(pd1);
		
		Iterable<PresentationDraft> result = this.conferenceService.findPresentationDrafts(conf, 0);
		
		int counter = 0;
		if(result.iterator().hasNext()) {
			counter++;
		}
		Assert.assertEquals(1, counter);
	}

//	@Test
//	public void makePresentationDraftFinalUndeterminedTest() {
//		List<PresentationDraft> listUndetermined = new ArrayList<>();
//		PresentationDraft presUndetermined = new PresentationDraft();
//		presUndetermined.setLabel(Label.UNDETERMINED);
//		listUndetermined.add(presUndetermined);
//
//		Mockito.when(this.draftRepo.findPresentationDraftByLabel(Label.UNDETERMINED)).thenReturn(listUndetermined);
//		
//		int response412 = draftService.makePresentationDraftsFinal().getStatusCodeValue();
//		
//		Mockito.verify(this.draftRepo).findPresentationDraftByLabel(Label.UNDETERMINED);
//
//		Assert.assertEquals(412, response412);
//	}

	@Test
	public void findByLabelTest() {
		// arrange
		PresentationDraft pres = new PresentationDraft();
		pres.setLabel(PresentationDraft.Label.ACCEPTED);
		List<PresentationDraft> list = new ArrayList<>();
		list.add(pres);

		Mockito.when(this.draftRepo.findPresentationDraftByLabel(Label.ACCEPTED)).thenReturn(list);
		// act
		List<PresentationDraft> testList = (List<PresentationDraft>) this.draftService.findByLabel(2);
		// assert
		Mockito.verify(this.draftRepo).findPresentationDraftByLabel(Label.ACCEPTED);

		Assert.assertEquals(1, testList.size());
	}

	@Test
	public void changeLabelTestLabelAlreadyExist() {
		PresentationDraft pres = new PresentationDraft();
		pres.setLabel(Label.ACCEPTED);
		Optional<PresentationDraft> opt = Optional.of(pres);

		Mockito.when(this.draftRepo.findById((long) 2)).thenReturn(opt);

		int result = this.draftService.changeLabel((long) 2, 2);

		Assert.assertEquals(0, result);
	}

	@Test
	public void changeLabelTestSuccesOrIDNotFound() {
		Optional<PresentationDraft> opt = Optional.of(new PresentationDraft());
		Optional<PresentationDraft> opt2 = Optional.ofNullable(null);

		Mockito.when(this.draftRepo.findById((long) 2)).thenReturn(opt);
		Mockito.when(this.draftRepo.findById((long) 3)).thenReturn(opt2);

		int result = this.draftService.changeLabel(2, 1);
		int result2 = this.draftService.changeLabel(3, 2);

		Assert.assertEquals(1, result);
		Assert.assertEquals(-1, result2);

	}

	@Test
	public void findByIdTest() {
		Optional<PresentationDraft> opt = Optional.of(new PresentationDraft());
		opt.get().setId(1);

		Mockito.when(this.draftRepo.findById((long) 1)).thenReturn(opt);

		PresentationDraft testItem = this.draftService.findById((long) 1).get();

		Mockito.verify(this.draftRepo).findById((long) 1);

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
	public void deleteOkTest() {
		PresentationDraft pres = new PresentationDraft();
		Optional<PresentationDraft> opt = Optional.of(pres);
		Long id = 1L;
		
		Mockito.when(this.draftRepo.findById(id)).thenReturn(opt);

		Boolean result = this.draftService.delete(id);

		Mockito.verify(this.draftRepo).deleteById(id);

		Assert.assertEquals(true, result);

	}
	
	@Test
	public void deleteFailedTest() {
		Optional<PresentationDraft> opt = Optional.ofNullable(null);
		Long id = 1L;
		
		Mockito.when(this.draftRepo.findById(id)).thenReturn(opt);

		Boolean result = this.draftService.delete(id);

		Mockito.verify(this.draftRepo, Mockito.times(0)).deleteById(id);
		
		Assert.assertEquals(false, result);

	}

	@Test
	public void findByCategoryTest() {
		PresentationDraft pres = new PresentationDraft();
		pres.setCategory("test");

		List<PresentationDraft> list = new ArrayList<>();
		list.add(pres);

		Mockito.when(this.draftRepo.findPresentationDraftByCategory("test")).thenReturn(list);

		List<PresentationDraft> testList = (List<PresentationDraft>) this.draftService.findByCategory("test");

		Mockito.verify(this.draftRepo).findPresentationDraftByCategory(Mockito.anyString());

		Assert.assertEquals(1, testList.size());

	}

	@Test
	public void findAllTest() {
		// arrange
		PresentationDraft pres = new PresentationDraft();
		List<PresentationDraft> list = new ArrayList<>();
		list.add(pres);

		Mockito.when(this.draftRepo.findAll()).thenReturn(list);
		// act
		List<PresentationDraft> testList = (List<PresentationDraft>) this.draftService.findAll();
		// assert
		Mockito.verify(this.draftRepo).findAll();

		Assert.assertEquals(1, testList.size());

	}

}
