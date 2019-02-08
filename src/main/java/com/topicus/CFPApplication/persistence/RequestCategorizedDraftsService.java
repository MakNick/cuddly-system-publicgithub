package com.topicus.CFPApplication.persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.topicus.CFPApplication.domain.PresentationDraft;
import com.topicus.CFPApplication.domain.PresentationDraftConference;
import com.topicus.CFPApplication.domain.conference.Conference;

@Service
@Transactional
public class RequestCategorizedDraftsService {

	private ConferenceService conferenceService;
	private PresentationDraftConference presentationDraftConference;
	
	@Autowired
	public RequestCategorizedDraftsService(ConferenceService conferenceService) {
		this.conferenceService = conferenceService;
	}
	
	//Method gets called when both Conference and Category exist
	public Iterable<PresentationDraft> findPresentationDraftsByCategory(Conference conference, String category){
		Iterable<PresentationDraft> allDraftsFromConference = conferenceService.findPresentationDrafts(conference, 5);
		presentationDraftConference = new PresentationDraftConference();
		
		for(PresentationDraft pdraft : allDraftsFromConference) {
			if(pdraft.getCategory().equals(category)) {
				presentationDraftConference.addPresentationDraft(pdraft);
			}
		}
		return presentationDraftConference.getPresentationDrafts();
	}
	
	
}
