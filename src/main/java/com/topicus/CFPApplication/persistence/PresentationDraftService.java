package com.topicus.CFPApplication.persistence;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;


import com.topicus.CFPApplication.config.paging.PagingConstants;
import com.topicus.CFPApplication.domain.conference.Conference;
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

    public Page<PresentationDraft> findAllBySearchCriteria(long conferenceId, String subject, String category, int labelId, int page, int limit) {

        List<Integer> pageConfigs = PagingConstants.defaultPageConfigurations(page, limit);

        Pageable pageRequest = PageRequest.of(pageConfigs.get(0), pageConfigs.get(1));

        Label correctLabel = null;

        for(Label label : Label.values()){
            if(labelId == label.ordinal()){
                correctLabel = label;
            }
        }

        if(subject.isEmpty() && category.isEmpty() && labelId == -1 || labelId == 5){
            return this.presentationDraftRepository.findPresentationDraftByConferenceIdOrderByLabelDesc(conferenceId,pageRequest);
        }

        if(subject.isEmpty()){
            if(category.isEmpty()){ // filter by label
                return this.presentationDraftRepository.findPresentationDraftByConferenceIdAndLabelOrderBySubject(conferenceId, correctLabel, pageRequest);
            }else if(labelId != -1){ // filter by category and label
                return this.presentationDraftRepository.findPresentationDraftByConferenceIdAndCategoryAndLabelOrderBySubject(conferenceId, category, correctLabel, pageRequest);
            }else{ // filter by category
                return this.presentationDraftRepository.findPresentationDraftByConferenceIdAndCategoryOrderByLabelDesc(conferenceId, category, pageRequest);

            }
        }else{
            if(category.isEmpty() && labelId == -1){ // filter by subject
                return this.presentationDraftRepository.findPresentationDraftByConferenceIdAndSubjectContainingOrderByLabelDesc(conferenceId,subject,pageRequest);
            }else if(category.isEmpty()){ // filter by subject and label
                return this.presentationDraftRepository.findPresentationDraftByConferenceIdAndSubjectContainingAndLabel(conferenceId,subject,correctLabel,pageRequest);
            }else if (labelId == -1){ // filter by subject and category
                return this.presentationDraftRepository.findPresentationDraftByConferenceIdAndSubjectContainingAndCategoryOrderByLabelDesc(conferenceId,subject,category,pageRequest);
            }
        }

        return this.presentationDraftRepository.findPresentationDraftByConferenceIdAndSubjectContainingAndCategoryAndLabel(conferenceId,subject,category,correctLabel,pageRequest);

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

    // TODO: Methode moet nog geïmplemeneerd en opgeschoond worden bij Finalizen
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
