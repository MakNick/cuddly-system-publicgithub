package com.topicus.CFPApplication.persistence;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.topicus.CFPApplication.domain.Applicant;
import com.topicus.CFPApplication.domain.Presentation;
import com.topicus.CFPApplication.domain.PresentationDraft;
import com.topicus.CFPApplication.domain.PresentationDraft.Label;
import com.topicus.CFPApplication.domain.conference.Conference;

@Service
@Transactional
public class PresentationService {

	private PresentationRepository presentationRepository;
	private ConferenceRepository conferenceRepository;
	private ApplicantService applicantService;

	@Autowired
	public PresentationService(PresentationRepository presentationRepository,
			ConferenceRepository conferenceRepository, ApplicantService applicantService) {
		this.presentationRepository = presentationRepository;
		this.conferenceRepository = conferenceRepository;
		this.applicantService = applicantService;
	}

	public List<Presentation> makePresentation(ArrayList<PresentationDraft> acceptedPresentationDrafts,
			Conference conference) {
		List<Presentation> presentationList = new ArrayList<Presentation>();
		for (PresentationDraft draft : acceptedPresentationDrafts) {
			if(draft.getLabel() == Label.ACCEPTED) {
				Presentation presentation = new Presentation();
				save(presentation);
				presentation.setSubject(draft.getSubject());
				presentation.setCategory(draft.getCategory());
				presentation.setSummary(draft.getSummary());
				presentation.setType(draft.getType());
				presentation.setDuration(draft.getDuration());
				for(Applicant applicant : draft.getApplicants()) {
					presentation.addApplicant(applicant);
					applicant.addPresentation(presentation);
					applicantService.save(applicant);
				}
				save(presentation);
				conference.addPresentation(presentation);
				conferenceRepository.save(conference);
				presentationList.add(presentation);
			}
		}
		return presentationList;
	}

	public Presentation save(Presentation presentation) {
		return presentationRepository.save(presentation);
	}

	public Iterable<Presentation> findAll() {
		Iterable<Presentation> result = presentationRepository.findAll();
		return result;
	}

}
