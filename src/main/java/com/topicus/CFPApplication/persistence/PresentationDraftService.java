package com.topicus.CFPApplication.persistence;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;


import com.topicus.CFPApplication.config.paging.PagingConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.topicus.CFPApplication.domain.Applicant;
import com.topicus.CFPApplication.domain.PresentationDraft;
import com.topicus.CFPApplication.domain.PresentationDraft.Label;

@Service
@Transactional
public class PresentationDraftService {

    private PresentationDraftRepository presentationDraftRepository;

    private final List<Label> labelList = Arrays.asList(Label.UNLABELED, Label.DENIED, Label.ACCEPTED, Label.RESERVED,
            Label.UNDETERMINED);

    @Autowired
    public PresentationDraftService(PresentationDraftRepository presentationDraftRepository) {
        this.presentationDraftRepository = presentationDraftRepository;
    }

    public Page<PresentationDraft> findAllByConferenceId(Long conferenceId, int page, int limit) {

        List<Integer> pageConfigs = PagingConstants.defaultPageConfigurations(page, limit);

        Pageable pageableRequest = PageRequest.of(pageConfigs.get(0), pageConfigs.get(1));

        return presentationDraftRepository.findPresentationDraftByConferenceIdOrderByLabelDesc(conferenceId, pageableRequest);

    }

    public PresentationDraft save(PresentationDraft presentationDraft) {
        return presentationDraftRepository.save(presentationDraft);
    }

    public Optional<PresentationDraft> findById(Long id) {
        return presentationDraftRepository.findById(id);
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

    public Page<PresentationDraft> findPresentationDraftsByLabel(Long conferenceId, byte labelId, int page, int limit) {

        List<Integer> pageConfigs = PagingConstants.defaultPageConfigurations(page, limit);

        Pageable pageableRequest = PageRequest.of(pageConfigs.get(0), pageConfigs.get(1));

        if (labelId == 5) {
            return this.presentationDraftRepository.findPresentationDraftByConferenceIdOrderByLabelDesc(conferenceId, pageableRequest);
        }

        for (Label label : Label.values()) {
            if (labelId == label.ordinal()) {
                return this.presentationDraftRepository.findPresentationDraftByConferenceIdAndLabelOrderBySubject(conferenceId, label, pageableRequest);
            }
        }
        throw new RuntimeException("Could not find a presentation draft with the label id of " + labelId);
    }

    public Page<PresentationDraft> findPresentationDraftsByCategory(Long conferenceId, String category, int page, int limit) {

        List<Integer> pageConfigs = PagingConstants.defaultPageConfigurations(page, limit);

        Pageable pageableRequest = PageRequest.of(pageConfigs.get(0), pageConfigs.get(1));

        return this.presentationDraftRepository.findPresentationDraftByConferenceIdAndCategoryOrderBySubject(conferenceId, category, pageableRequest);
    }

    public Page<PresentationDraft> findPresentationDraftsByCategoryAndLabel(Long conferenceId, String category, byte labelId, int page, int limit) {

        List<Integer> pageConfigs = PagingConstants.defaultPageConfigurations(page, limit);

        Pageable pageableRequest = PageRequest.of(pageConfigs.get(0), pageConfigs.get(1));

        if (labelId == 5) {
            return this.findPresentationDraftsByCategory(conferenceId, category, page, limit);
        }

        for (Label label : Label.values()) {
            if (labelId == label.ordinal()) {
                return this.presentationDraftRepository.findPresentationDraftByConferenceIdAndCategoryAndLabel(conferenceId, category, label, pageableRequest);
            }
        }

        throw new RuntimeException("Could not find a presentation draft with the label id of " + labelId);
    }

//	public List<PresentationDraft> makePresentationDraftsFinal(long conferenceId, int label)
//			throws CannotProceedException,NoSuchElementException {
//		Optional<Conference> conference = conferenceService.findById(conferenceId);
//		if (conference.isPresent()) {
//			if (LocalDateTime.now().isBefore(conference.get().getDeadlinePresentationDraft())) {
//				throw new CannotProceedException();
//			} else if (conferenceService.findPresentationDrafts(conference.get(), 0).iterator().hasNext()
//					|| conferenceService.findPresentationDrafts(conference.get(), 4).iterator().hasNext()) {
//				throw new CannotProceedException();
//			} else {
//				List<PresentationDraft> listPresentationDrafts = new ArrayList<>();
//				Iterator<PresentationDraft> tempList = conferenceService.findPresentationDrafts(conference.get(), label)
//						.iterator();
//				while (tempList.hasNext()) {
//					listPresentationDrafts.add(tempList.next());
//				}
//				return listPresentationDrafts;
//			}
//		} else {
//			throw new NoSuchElementException();
//		}
//	}
}
