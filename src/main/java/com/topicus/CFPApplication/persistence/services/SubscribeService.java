package com.topicus.CFPApplication.persistence.services;

import java.util.Optional;
import java.util.Set;

import com.topicus.CFPApplication.persistence.repositories.ApplicantRepository;
import com.topicus.CFPApplication.persistence.repositories.ConferenceRepository;
import com.topicus.CFPApplication.persistence.repositories.PresentationDraftRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.topicus.CFPApplication.domain.Applicant;
import com.topicus.CFPApplication.domain.PresentationDraft;
import com.topicus.CFPApplication.domain.Conference;

@Service
@Transactional
public class SubscribeService {

	private PresentationDraftRepository presentationDraftRepository;
	private ConferenceRepository conferenceRepository;
	private ApplicantService applicantService;
	private ApplicantRepository applicantRepository;

	@Autowired
	public SubscribeService(PresentationDraftRepository presentationDraftRepository,
			ConferenceRepository conferenceRepository, ApplicantService applicantService,
			ApplicantRepository applicantRepository) {
		this.presentationDraftRepository = presentationDraftRepository;
		this.conferenceRepository = conferenceRepository;
		this.applicantService = applicantService;
		this.applicantRepository = applicantRepository;
	}

	public Conference linkPresentationDraftWithConference(Conference conference, PresentationDraft presentationDraft) {
		conference.addPresentationDraft(presentationDraft);
		presentationDraftRepository.save(presentationDraft);
		return conferenceRepository.save(conference);
	}

	public PresentationDraft linkPresentationDraftWithApplicants(PresentationDraft presentationDraft,
			Set<Applicant> applicants) {
		presentationDraftRepository.save(presentationDraft);
		for (Applicant applicant : applicants) {
			Optional<Applicant> result = applicantRepository.findApplicantByNameAndEmail(applicant.getName(),
					applicant.getEmail());
			if (result.isPresent()) {
				result.get().addPresentationDraft(presentationDraft);
				presentationDraft.addApplicant(result.get());
				presentationDraftRepository.save(presentationDraft);
			} else {
				applicantService.save(applicant);
				presentationDraft.addApplicant(applicant);
				applicant.addPresentationDraft(presentationDraft);
				presentationDraftRepository.save(presentationDraft);
				applicantService.save(applicant);
			}
		}
		return presentationDraft;
	}

}
