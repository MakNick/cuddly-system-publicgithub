package com.topicus.CFPApplication.persistence.services;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;


import com.topicus.CFPApplication.constants.PagingConstants;
import com.topicus.CFPApplication.domain.Conference;
import com.topicus.CFPApplication.persistence.repositories.ConferenceRepository;
import com.topicus.CFPApplication.persistence.repositories.PresentationDraftRepository;
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
    private ConferenceRepository conferenceRepository;

    private final List<Label> labelList = Arrays.asList(Label.UNLABELED, Label.DENIED, Label.ACCEPTED, Label.RESERVED,
            Label.UNDETERMINED);

    @Autowired
    public PresentationDraftService(PresentationDraftRepository presentationDraftRepository, ConferenceRepository conferenceRepository) {
        this.presentationDraftRepository = presentationDraftRepository;
        this.conferenceRepository = conferenceRepository;
    }

    public Page<PresentationDraft> findAllByConferenceId(Long conferenceId, int page, int limit) {

        List<Integer> pageConfigs = PagingConstants.defaultPageConfigurations(page, limit);

        Pageable pageableRequest = PageRequest.of(pageConfigs.get(0), pageConfigs.get(1));

        return presentationDraftRepository.findPresentationDraftByConferenceIdOrderByLabelDesc(conferenceId, pageableRequest);

    }

    public Page<PresentationDraft> findAllBySearchCriteria(long conferenceId, String subject, String category, String label, int page, int limit) {

        List<Integer> pageConfigs = PagingConstants.defaultPageConfigurations(page, limit);

        Pageable pageRequest = PageRequest.of(pageConfigs.get(0), pageConfigs.get(1));

        return this.presentationDraftRepository.searchPresentationDrafts(conferenceId, subject.equals("empty") ? null : subject, category.equals("empty") ? null : category, label.equals("empty") ? null : label, pageRequest);
    }

    public PresentationDraft save(long conferenceId, PresentationDraft presentationDraft) {

        Conference currentConference = this.conferenceRepository.findById(conferenceId)
                .orElseThrow(() -> new RuntimeException("The current conference could not be found"));
        presentationDraft.setConference(currentConference);
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
}
