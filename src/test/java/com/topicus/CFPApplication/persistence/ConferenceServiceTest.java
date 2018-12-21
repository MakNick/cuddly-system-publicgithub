package com.topicus.CFPApplication.persistence;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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
public class ConferenceServiceTest {

	@Mock
	ConferenceRepository conferenceRepo;

	@InjectMocks
	ConferenceService conferenceService;

	@Test
	public void findAllTest() {
		Conference conf = new Conference();
		List<Conference> list = new ArrayList<>();
		list.add(conf);

		Mockito.when(this.conferenceRepo.findAll()).thenReturn(list);
		List<Conference> testList = (List<Conference>) this.conferenceService.findAll();

		Mockito.verify(this.conferenceRepo).findAll();

		Assert.assertEquals(1, testList.size());
	}

	@Test
	public void saveTest() {
		Conference conf = new Conference();

		Mockito.when(this.conferenceRepo.save(conf)).thenReturn(conf);

		Conference testItem = this.conferenceRepo.save(conf);

		Mockito.verify(this.conferenceRepo).save(conf);

		Assert.assertEquals(conf, testItem);
	}

	@Test
	public void findByIdTest() {
		Optional<Conference> opt = Optional.of(new Conference());
		opt.get().setId(1);

		Mockito.when(this.conferenceRepo.findById((long) 1)).thenReturn(opt);

		Conference testItem = this.conferenceService.findById((long) 1).get();

		Mockito.verify(this.conferenceRepo).findById((long) 1);

		Assert.assertEquals(1, testItem.getId());
	}
	
	@Test
	public void findPresentationDraftTest() {
		
		PresentationDraft d1 = new PresentationDraft();
		PresentationDraft d2 = new PresentationDraft();
		PresentationDraft d3 = new PresentationDraft();
		
		List<Label> labelList = Arrays.asList(Label.UNLABELED, Label.DENIED, Label.ACCEPTED, Label.RESERVED, Label.UNDETERMINED);
		
		for (int a = 0 ; a <= 5 ; a++) {
			Conference c = new Conference();
			if (a == 5) {
				c.addPresentationDraft(d1);
				c.addPresentationDraft(d2);
				c.addPresentationDraft(d3);
			} else {
				d1.setLabel(labelList.get(a)); c.addPresentationDraft(d1);
				d2.setLabel(labelList.get(a)); c.addPresentationDraft(d2);
				d3.setLabel(labelList.get(a)); c.addPresentationDraft(d3);
			}
			
			Set<PresentationDraft> testList = (Set<PresentationDraft>) this.conferenceService.findPresentationDrafts(c, a);
			
			Assert.assertEquals(3, testList.size());
		}
	}
}
