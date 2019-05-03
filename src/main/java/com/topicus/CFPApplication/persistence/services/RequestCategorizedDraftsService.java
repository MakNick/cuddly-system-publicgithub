package com.topicus.CFPApplication.persistence.services;

import com.topicus.CFPApplication.persistence.repositories.PresentationDraftRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.topicus.CFPApplication.domain.PresentationDraftConference;

@Service
@Transactional
public class RequestCategorizedDraftsService {

	private PresentationDraftRepository presentationDraftRepository;

	private PresentationDraftConference presentationDraftConference;

	@Autowired
	public RequestCategorizedDraftsService(PresentationDraftRepository presentationDraftRepository) {
		this.presentationDraftRepository = presentationDraftRepository;
	}

	//Method gets called when both Conference and Category exist
//	public Iterable<PresentationDraftRequest> findPresentationDraftsByCategory(Conference conference, String category){
//		Iterable<PresentationDraftRequest> allDraftsFromConference = this.presentationDraftRepository.findPresentationDraftByConferenceId(conference.getId());
//		presentationDraftConference = new PresentationDraftConference();
//
//		for(PresentationDraftRequest pdraft : allDraftsFromConference) {
//			if(pdraft.getCategory().equals(category)) {
//				presentationDraftConference.addPresentationDraft(pdraft);
//			}
//		}
//		return presentationDraftConference.getPresentationDrafts();
//	}

	
}
