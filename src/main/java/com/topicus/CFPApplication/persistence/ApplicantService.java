package com.topicus.CFPApplication.persistence;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.topicus.CFPApplication.domain.Applicant;

@Service
@Transactional
public class ApplicantService {

	private ApplicantRepository applicantRepository;
	
	@Autowired
	public ApplicantService(ApplicantRepository applicantRepository) {
		this.applicantRepository = applicantRepository;
	}

	public Iterable<Applicant> findAll() {
		Iterable<Applicant> result = applicantRepository.findAll();
		return result;
	}

	public Applicant save(Applicant applicant) {
		return applicantRepository.save(applicant);
	}

	public Optional<Applicant> findById(Long id) {
		return applicantRepository.findById(id);
	}

	public Optional<Applicant> findExistingApplicant(String name, String email) {
		return applicantRepository.findApplicantByNameAndEmail(name, email);
	}
	
	public Optional<Applicant> showPresentationDraftsByApplicant(Long id) {
		return applicantRepository.findById(id);
	}

}
