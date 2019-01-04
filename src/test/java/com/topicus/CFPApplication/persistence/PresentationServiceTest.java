package com.topicus.CFPApplication.persistence;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.topicus.CFPApplication.domain.Conference;
import com.topicus.CFPApplication.domain.Presentation;
import com.topicus.CFPApplication.domain.PresentationDraft;
import com.topicus.CFPApplication.domain.PresentationDraft.Label;

@RunWith(MockitoJUnitRunner.class)
public class PresentationServiceTest {

	@InjectMocks
	PresentationService presentationService;

	@Mock
	PresentationRepository presentationRepository;

	@Mock
	ConferenceRepository conferenceRepository;

	@Test
	public void makePresentationTest() {
		Conference conf = new Conference();
		PresentationDraft presentationDraft1 = new PresentationDraft();
		presentationDraft1.setLabel(Label.ACCEPTED);
		PresentationDraft presentationDraft2 = new PresentationDraft();
		presentationDraft2.setLabel(Label.ACCEPTED);
		conf.addPresentationDraft(presentationDraft1);
		conf.addPresentationDraft(presentationDraft2);

		ArrayList<PresentationDraft> listPresentationDrafts = new ArrayList<>();
		Iterator<PresentationDraft> tempList = conf.getPresentationDrafts().iterator();
		while (tempList.hasNext()) {
			listPresentationDrafts.add(tempList.next());
		}

		for (Presentation presentation : presentationService.makePresentation(listPresentationDrafts, conf)) {
			conf.addPresentation(presentation);
		}
		System.out.println(conf.getPresentations().size());
		Assert.assertEquals(2, conf.getPresentations().size());
	}

	@Test
	public void saveTest() {
		Presentation presentation = new Presentation();
		Mockito.when(this.presentationRepository.save(presentation)).thenReturn(presentation);
		Presentation testPresentation = this.presentationService.save(presentation);
		Mockito.verify(this.presentationRepository).save(Mockito.any(Presentation.class));
		Assert.assertEquals(presentation, testPresentation);
	}

	@Test
	public void findAllTest() {
		Presentation presentation = new Presentation();
		List<Presentation> presentationList = new ArrayList<Presentation>();
		presentationList.add(presentation);
		Mockito.when(this.presentationRepository.findAll()).thenReturn(presentationList);
		List<Presentation> presentationListTest = (List<Presentation>) this.presentationService.findAll();
		Mockito.verify(this.presentationRepository).findAll();
		Assert.assertEquals(1, presentationListTest.size());
	}
}