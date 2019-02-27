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

import com.topicus.CFPApplication.domain.PresentationDraft;
import com.topicus.CFPApplication.domain.PresentationDraft.Label;
import com.topicus.CFPApplication.domain.conference.Conference;

@RunWith(MockitoJUnitRunner.class)
public class ConferenceServiceTest {

	@Mock
	ConferenceRepository conferenceRepo;

	@InjectMocks
	ConferenceService conferenceService;

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
}
