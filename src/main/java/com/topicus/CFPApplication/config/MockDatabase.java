package com.topicus.CFPApplication.config;

import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.topicus.CFPApplication.domain.Conference;
import com.topicus.CFPApplication.persistence.ConferenceRepository;

@Component
public class MockDatabase {

	private ConferenceRepository conferenceRepository;

	@Autowired
	public MockDatabase(ConferenceRepository conferenceRepository) {
		this.conferenceRepository = conferenceRepository;
	}

	@PostConstruct
	public void init() {
		fillConferenceListCategories();
	}

	private void fillConferenceListCategories() {
		Optional<Conference> result = conferenceRepository.findById((long) 1);
		Set<String> categories = new TreeSet<>();
		if (result.isPresent()) {
			categories.add("Rood");
			categories.add("Blauw");
			categories.add("Geel");
			result.get().setCategories(categories);
			conferenceRepository.save(result.get());
		} else {
			Conference conference = new Conference();
			conference.setName("January2020Conference");
			categories.add("Rood");
			categories.add("Blauw");
			categories.add("Geel");
			conference.setCategories(categories);
			conferenceRepository.save(conference);
		}

	}

}
