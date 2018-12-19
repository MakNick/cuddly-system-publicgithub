package com.topicus.CFPApplication.persistence;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.topicus.CFPApplication.domain.Applicant;
import com.topicus.CFPApplication.domain.PresentationDraft;
import com.topicus.CFPApplication.domain.PresentationDraft.Label;

@Service
@Transactional
public class PresentationDraftService {

	private PresentationDraftRepository presentationDraftRepository;
	private ApplicantRepository applicantRepository;
	private ApplicantService applicantService;

	@Autowired
	public PresentationDraftService(PresentationDraftRepository presentationDraftRepository,
			ApplicantRepository applicantsRepository, ApplicantService applicantService) {
		this.presentationDraftRepository = presentationDraftRepository;
		this.applicantRepository = applicantsRepository;
		this.applicantService = applicantService;

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

	public boolean changeLabel(long id, int value) {
		PresentationDraft temp = presentationDraftRepository.findById(id).get();
		switch (value) {
		case 1:
			if (temp.getLabel().equals(Label.DENIED)) {
				return false;
			} else {
				temp.setLabel(Label.DENIED);
				return true;
			}
		case 2:
			if (temp.getLabel().equals(Label.ACCEPTED)) {
				return false;
			} else {
				temp.setLabel(Label.ACCEPTED);
				return true;
			}
		case 3:
			if (temp.getLabel().equals(Label.RESERVED)) {
				return false;
			} else {
				temp.setLabel(Label.RESERVED);
				return true;
			}
		case 4:
			if (temp.getLabel().equals(Label.UNDETERMINED)) {
				return false;
			} else {
				temp.setLabel(Label.UNDETERMINED);
				return true;
			}
		default:
			return false;
		}
	}

//	 de applicant mag niet worden verwijderd, hij moet in de database blijven. De presentatie moet wel uit de lijst van de applicant.
	public void delete(long id) {
		Optional<PresentationDraft> opt = presentationDraftRepository.findById(id);
		PresentationDraft result = opt.get();

		for (Applicant applicant : result.getApplicants()) {
			if (applicant.getPresentationDrafts().size() == 1) {
				applicantRepository.delete(applicant);
			} else {
				applicant.getPresentationDrafts().remove(result);
			}
		}
		presentationDraftRepository.deleteById(id);
	}

	public Iterable<PresentationDraft> findByLabel(int value) {
		switch (value) {
		case 0:
			return presentationDraftRepository.findPresentationDraftByLabel(Label.UNLABELED);
		case 1:
			return presentationDraftRepository.findPresentationDraftByLabel(Label.DENIED);
		case 2:
			return presentationDraftRepository.findPresentationDraftByLabel(Label.ACCEPTED);
		case 3:
			return presentationDraftRepository.findPresentationDraftByLabel(Label.RESERVED);
		case 4:
			return presentationDraftRepository.findPresentationDraftByLabel(Label.UNDETERMINED);
		default:
			return presentationDraftRepository.findAll();
		}
	}

	public ResponseEntity makePresentationDraftsFinal() {
		if (LocalDateTime.now().isBefore(LocalDateTime.of(2005, 9, 2, 1, 15))) {
			return new ResponseEntity<>("deadline not passed", HttpStatus.PRECONDITION_FAILED);
		} else if (!((ArrayList<PresentationDraft>) findByLabel(0)).isEmpty()
				|| !((ArrayList<PresentationDraft>) findByLabel(4)).isEmpty()) {
			return new ResponseEntity<>("still unlabeled or undetermined PresentationDrafts",
					HttpStatus.PRECONDITION_FAILED);
		} else {
			return ResponseEntity.status(200).build();
		}
	}

}
