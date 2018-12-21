package com.topicus.CFPApplication.persistence;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.topicus.CFPApplication.domain.Conference;
import com.topicus.CFPApplication.domain.PresentationDraft;
import com.topicus.CFPApplication.domain.PresentationDraft.Label;

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
	
	public Iterable<PresentationDraft> findPresentationDrafts(Conference conference, int label) {
		Set<PresentationDraft> set = conference.getPresentationDrafts();
		
		Label L = null;
		Label L2 = null;
		switch (label) {
		case 0:
			L = Label.UNLABELED;
			L2 = Label.UNDETERMINED;
			break;
		case 1:
			L = Label.DENIED;
			break;
		case 2:
			L = Label.ACCEPTED;
			break;
		case 3:
			L = Label.RESERVED;
			break;
		case 4:
			L = Label.UNDETERMINED;
			L2 = Label.UNLABELED;
			break;
		case 5:
			return set;
		}
		
		Set<PresentationDraft> sendset = new HashSet<PresentationDraft>();
		
		for (PresentationDraft p : set) {
			if (p.getLabel() == L || p.getLabel() == L2) {
				sendset.add(p);
				}
			}	
		return sendset;		
		}
	
	
	}

