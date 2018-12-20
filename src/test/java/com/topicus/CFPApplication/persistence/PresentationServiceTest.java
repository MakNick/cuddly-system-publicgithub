package com.topicus.CFPApplication.persistence;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.topicus.CFPApplication.domain.Presentation;
import com.topicus.CFPApplication.domain.PresentationDraft;

@RunWith(MockitoJUnitRunner.class)
public class PresentationServiceTest {

	@InjectMocks
	PresentationService presentationService;
	
	@Mock
	PresentationRepository presentationRepository;
	
	public void makePresentationTest() {
		PresentationDraft presentationDraft1 = new PresentationDraft();
		presentationDraft1.setDuration(60);
		PresentationDraft presentationDraft2 = new PresentationDraft();
		ArrayList<PresentationDraft> presentationDraftList = new ArrayList<PresentationDraft>();
		presentationDraftList.add(presentationDraft1);
		presentationDraftList.add(presentationDraft2);
		List<Presentation> presentationListTest = this.presentationService.makePresentation(presentationDraftList);
		Assert.assertEquals(2, presentationListTest.size());
		Assert.assertEquals(60, presentationListTest.get(0).getDuration());		
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
		List<Presentation> presentationListTest = (List<Presentation>)this.presentationService.findAll();
		Mockito.verify(this.presentationRepository).findAll();
		Assert.assertEquals(1, presentationListTest.size());
	}
}