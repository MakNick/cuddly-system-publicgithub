package com.topicus.CFPApplication.persistence;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.naming.CannotProceedException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.topicus.CFPApplication.domain.Applicant;
import com.topicus.CFPApplication.domain.Conference;
import com.topicus.CFPApplication.domain.PresentationDraft;
import com.topicus.CFPApplication.domain.PresentationDraft.Label;

@Service
@Transactional
public class PresentationDraftService {

	private PresentationDraftRepository presentationDraftRepository;
	private ConferenceService conferenceService;

	private final List<Label> labelList = Arrays.asList(Label.UNLABELED, Label.DENIED, Label.ACCEPTED, Label.RESERVED,
			Label.UNDETERMINED);

	@Autowired
	public PresentationDraftService(PresentationDraftRepository presentationDraftRepository,
			ConferenceService conferenceService) {
		this.presentationDraftRepository = presentationDraftRepository;
		this.conferenceService = conferenceService;
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

	public List<PresentationDraft> makePresentationDraftsFinal(long conferenceId, int label)
			throws CannotProceedException,NoSuchElementException {
		Optional<Conference> conference = conferenceService.findById(conferenceId);
		if (conference.isPresent()) {
			if (LocalDateTime.now().isBefore(conference.get().getDeadlinePresentationDraft())) {
				throw new CannotProceedException();
			} else if (conferenceService.findPresentationDrafts(conference.get(), 0).iterator().hasNext()
					|| conferenceService.findPresentationDrafts(conference.get(), 4).iterator().hasNext()) {
				throw new CannotProceedException();
			} else {
				List<PresentationDraft> listPresentationDrafts = new ArrayList<>();
				Iterator<PresentationDraft> tempList = conferenceService.findPresentationDrafts(conference.get(), label)
						.iterator();
				while (tempList.hasNext()) {
					listPresentationDrafts.add(tempList.next());
				}
				return listPresentationDrafts;
			}
		} else {
			throw new NoSuchElementException();
		}
	}
}