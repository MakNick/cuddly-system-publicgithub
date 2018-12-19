package com.topicus.CFPApplication.persistence;

import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.topicus.CFPApplication.domain.Applicant;
import com.topicus.CFPApplication.domain.Conference;
import com.topicus.CFPApplication.domain.PresentationDraft;

@Service
@Transactional
public class SubscribeService {

	@Autowired
	private PresentationDraftRepository presentationDraftRepository;

	@Autowired
	private ConferenceRepository conferenceRepository;

	@Autowired
	private ApplicantService applicantService;

	@Autowired
	private ApplicantRepository applicantRepository;

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
