package com.topicus.CFPApplication.persistence;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
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

import com.topicus.CFPApplication.domain.PresentationDraft;
import com.topicus.CFPApplication.domain.PresentationDraft.Label;
import com.topicus.CFPApplication.domain.conference.Conference;

@RunWith(MockitoJUnitRunner.class)
public class PresentationDraftServiceTest {

	@Mock
	PresentationDraftRepository draftRepo;

	@InjectMocks
	PresentationDraftService draftService;

	@InjectMocks
	SubscribeService subscribeService;

	@Mock
	ApplicantRepository applicantRepo;

	@InjectMocks
	ApplicantService applicantService;

	@Mock
	PresentationRepository presentationRepo;

	@InjectMocks
	PresentationService presentationService;

	@Mock
	ConferenceRepository conferenceRepo;

	@Mock
	ConferenceService conferenceService;

	@Test
	public void makePresentationFinalDeadlineTest() {
		Conference conf = new Conference();
		conf.setId(1);
		conf.setDeadlinePresentationDraft(LocalDateTime.now().plusHours(1));

		PresentationDraft pd1 = new PresentationDraft();
		pd1.setLabel(Label.UNLABELED);
		conf.addPresentationDraft(pd1);

		Mockito.when(this.conferenceService.findById((long) 1)).thenReturn(Optional.of(conf));

		int result = 0;
		try {
			this.draftService.makePresentationDraftsFinal(conf.getId(), 0);
			result = 1;
		} catch (CannotProceedException e) {
			result = 2;
		} catch (NoSuchElementException e) {
			result = 3;
		}

		Assert.assertEquals(2, result);
	}

	@Test
	public void makePresentationFinalUnlabeledTest() {
		final List<Label> labelList = Arrays.asList(Label.UNLABELED, Label.DENIED, Label.ACCEPTED, Label.RESERVED,
				Label.UNDETERMINED);

		Conference conf = new Conference();
		conf.setId(1);
		conf.setDeadlinePresentationDraft(LocalDateTime.now().minusHours(1));

		for (int i = 0; i < labelList.size(); i++) {
			PresentationDraft pd = new PresentationDraft();
			pd.setLabel(labelList.get(i));
			conf.addPresentationDraft(pd);
		}

		Mockito.when(this.conferenceService.findById((long) 1)).thenReturn(Optional.of(conf));
		Mockito.when(this.conferenceService.findPresentationDrafts(conf, 0)).thenReturn(conf.getPresentationDrafts());

		int result = 0;
		List<PresentationDraft> resultList = new ArrayList<>();
		try {
			resultList.addAll(this.draftService.makePresentationDraftsFinal(conf.getId(), 0));
			result = 1;
		} catch (CannotProceedException e) {
			result = 2;
		} catch (NoSuchElementException e) {
			result = 3;
		}

		Assert.assertEquals(2, result);
	}

	@Test
	public void makePresentationDraftFinalTest() throws CannotProceedException, NoSuchElementException {
		Conference conf = new Conference();
		conf.setId(1);
		conf.setDeadlinePresentationDraft(LocalDateTime.now().minusHours(1));
		List<PresentationDraft> resultList = new ArrayList<>();

		PresentationDraft pd1 = new PresentationDraft();
		pd1.setLabel(Label.ACCEPTED);
		resultList.add(pd1);
		
		
		Mockito.when(this.conferenceService.findById((long) 1)).thenReturn(Optional.of(conf));
		Mockito.when(this.draftService.makePresentationDraftsFinal(conf.getId(), 2)).thenReturn(resultList);

		int result = 0;
		try {
			resultList = this.draftService.makePresentationDraftsFinal(conf.getId(), 2);
			result = 1;
		} catch (CannotProceedException e) {
			result = 2;
		} catch (NoSuchElementException e) {
			result = 3;
		}

		Assert.assertEquals(1, result);
		Assert.assertEquals(1, resultList.size());
	}

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
