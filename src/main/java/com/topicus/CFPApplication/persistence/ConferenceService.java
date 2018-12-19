package com.topicus.CFPApplication.persistence;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.topicus.CFPApplication.domain.Applicant;
import com.topicus.CFPApplication.domain.Conference;
import com.topicus.CFPApplication.domain.PresentationDraft;

@Service
@Transactional
public class ConferenceService {

	@Autowired
	private ConferenceRepository conferenceRepository;

	public Iterable<Conference> findAll() {
		Iterable<Conference> result = conferenceRepository.findAll();
		return result;
	}

	public Conference save(Conference conference) {
		return conferenceRepository.save(conference);
	}

	public Optional<Conference> findById(Long id) {
		return conferenceRepository.findById(id);
	}
	
	public void delete(long id) {
		conferenceRepository.deleteById(id);
	}

}
