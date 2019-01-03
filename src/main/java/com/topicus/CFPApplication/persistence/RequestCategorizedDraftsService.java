package com.topicus.CFPApplication.persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.topicus.CFPApplication.domain.Conference;
import com.topicus.CFPApplication.domain.PresentationDraft;
import com.topicus.CFPApplication.domain.PresentationDraftConference;

@Service
@Transactional
public class RequestCategorizedDraftsService {

	private PresentationDraftRepository pdrep;
	private PresentationDraftService pdser;
	private ConferenceRepository crep;
	private ConferenceService cser;
	private PresentationDraftConference pdconf;
	
	@Autowired
	public RequestCategorizedDraftsService(PresentationDraftRepository pdrep,
			ConferenceRepository crep, PresentationDraftService pdser, ConferenceService cser) {
		this.pdrep = pdrep;
		this.pdser = pdser;
		this.crep = crep;
		this.cser = cser;
	}
	
	//Method gets called when both Conference and Category exist
	public Iterable<PresentationDraft> findPresentationDraftsByCategory(Conference conference, String category){
		Iterable<PresentationDraft> allDraftsFromConference = cser.findPresentationDrafts(conference, 5);
		pdconf = new PresentationDraftConference();
		
		for(PresentationDraft pdraft : allDraftsFromConference) {
			if(pdraft.getCategory().equals(category)) {
				pdconf.addPresentationDraft(pdraft);
			}
		}
		return pdconf.getPresentationDrafts();
	}
	
	
}
