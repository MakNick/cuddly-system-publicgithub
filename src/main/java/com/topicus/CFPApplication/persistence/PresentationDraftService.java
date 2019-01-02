package com.topicus.CFPApplication.persistence;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.topicus.CFPApplication.domain.Applicant;
import com.topicus.CFPApplication.domain.Presentation;
import com.topicus.CFPApplication.domain.PresentationDraft;
import com.topicus.CFPApplication.domain.PresentationDraft.Label;

@Service
@Transactional
public class PresentationDraftService {

	private PresentationDraftRepository presentationDraftRepository;
	private PresentationService presentationService;

	private final List<Label> labelList = Arrays.asList(Label.UNLABELED, Label.DENIED, Label.ACCEPTED, Label.RESERVED,
			Label.UNDETERMINED);

	@Autowired
	public PresentationDraftService(PresentationDraftRepository presentationDraftRepository,
			PresentationService presentationService) {
		this.presentationDraftRepository = presentationDraftRepository;
		this.presentationService = presentationService;
	}

	public Iterable<PresentationDraft> findAll() {
		Iterable<PresentationDraft> result = presentationDraftRepository.findAll();
		return result;
	}

	public PresentationDraft save(PresentationDraft presentationDraft) {
		return presentationDraftRepository.save(presentationDraft);
	}

	public Optional<PresentationDraft> findById(Long id) {
		return presentationDraftRepository.findById(id);
	}

	public Iterable<PresentationDraft> findByCategory(String category) {
		return presentationDraftRepository.findPresentationDraftByCategory(category);
	}

	public int changeLabel(long id, int value) {
		Optional<PresentationDraft> result = presentationDraftRepository.findById(id);
		if (result.isPresent()) {
			PresentationDraft presentationDraft = result.get();
			if (labelList.get(value).equals(presentationDraft.getLabel())) {
				return 0;
			} else {
				presentationDraft.setLabel(labelList.get(value));
				return value;
			}
		}
		return -1;
	}

	public Boolean delete(long id) {
		Optional<PresentationDraft> opt = presentationDraftRepository.findById(id);
		if (opt.isPresent()) {
			PresentationDraft result = opt.get();
			for (Applicant applicant : result.getApplicants()) {
				applicant.getPresentationDrafts().remove(result);
			}
			presentationDraftRepository.deleteById(id);
			return true;
		}
		return false;
	}

	public Iterable<PresentationDraft> findByLabel(int value) {
		return presentationDraftRepository.findPresentationDraftByLabel(labelList.get(value));
	}

	public ResponseEntity<Object> makePresentationDraftsFinal() {
		if (LocalDateTime.now().isBefore(LocalDateTime.of(2005, 9, 2, 1, 15))) {
			return new ResponseEntity<>("deadline not passed", HttpStatus.PRECONDITION_FAILED);
		} else if (!((ArrayList<PresentationDraft>) findByLabel(0)).isEmpty()
				|| !((ArrayList<PresentationDraft>) findByLabel(4)).isEmpty()) {
			return new ResponseEntity<>("still unlabeled or undetermined PresentationDrafts",
					HttpStatus.PRECONDITION_FAILED);
		} else if (((List<Presentation>) presentationService.findAll()).isEmpty()) {
			
			
			
			
			ArrayList<PresentationDraft> acceptedPresentationDrafts = (ArrayList<PresentationDraft>) findByLabel(2);
			presentationService.makePresentation(acceptedPresentationDrafts);
			return ResponseEntity.status(200).build();
		} else {
			return ResponseEntity.status(418).build(); // tekst nog nader te bepalen
		}
	}

}
