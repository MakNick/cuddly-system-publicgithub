package com.topicus.CFPApplication.persistence;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.topicus.CFPApplication.domain.Applicant;
import com.topicus.CFPApplication.domain.Presentation;
import com.topicus.CFPApplication.domain.PresentationDraft;

@Service
@Transactional
public class PresentationService {
	
	@Autowired
	private PresentationRepository presentationRepository;
	
	public void makePresentation(ArrayList<PresentationDraft> acceptedPresentationDrafts) {
		for(PresentationDraft draft: acceptedPresentationDrafts) {
			Presentation presentation = new Presentation();
			presentation.setSubject(draft.getSubject());
			presentation.setCategory(draft.getCategory());
			presentation.setSummary(draft.getSummary());
			presentation.setType(draft.getType());
			presentation.setDuration(draft.getDuration());
			for (Applicant applicant: draft.getApplicants()) {
				presentation.addApplicant(applicant);
			}
			save(presentation);
		}
	}

	public Presentation save(Presentation presentation) {
		return presentationRepository.save(presentation);
	}
	
	public Iterable<Presentation> findAll(){
		Iterable<Presentation> result = presentationRepository.findAll();
		return result;
	}
	
}
