package com.topicus.CFPApplication.persistence;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.topicus.CFPApplication.domain.conference.Conference;

@Service
@Transactional
public class ConferenceService {

	private ConferenceRepository conferenceRepository;

	@Autowired
	public ConferenceService(ConferenceRepository conferenceRepository) {
		this.conferenceRepository = conferenceRepository;
	}

	public Iterable<Conference> findAll() {
		Iterable<Conference> result = conferenceRepository.findAllByOrderByName();
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
