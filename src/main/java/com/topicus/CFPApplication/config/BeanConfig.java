package com.topicus.CFPApplication.config;

import java.util.Set;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.topicus.CFPApplication.domain.Conference;
import com.topicus.CFPApplication.persistence.ConferenceRepository;

@Configuration
public class BeanConfig {
	
	ConferenceRepository conferenceRepo;
	
	@Autowired
	public BeanConfig(ConferenceRepository conferenceRepo) {
		this.conferenceRepo = conferenceRepo;
	}

	@Bean
	public Set<String> categories() {
		Set<String> categories = new TreeSet<String>((o1, o2) -> o1.toLowerCase().compareTo(o2.toLowerCase()));
		return categories;
	}
	
	@Bean
	public Set<Conference.Stage> stages() {
		Set<Conference.Stage> stages = new TreeSet<Conference.Stage>((o1, o2) -> o1.getName().toLowerCase().compareTo(o2.getName().toLowerCase()));
		return stages;
	}
	
	@Bean
	public Set<Conference.Extra> extras() {
		Set<Conference.Extra> extras = new TreeSet<Conference.Extra>((o1, o2) -> o1.getName().toLowerCase().compareTo(o2.getName().toLowerCase()));
		return extras;
	}

}
