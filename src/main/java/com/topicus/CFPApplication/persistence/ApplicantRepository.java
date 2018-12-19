package com.topicus.CFPApplication.persistence;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import com.topicus.CFPApplication.domain.Applicant;

@Component
public interface ApplicantRepository extends CrudRepository<Applicant, Long> {

	Optional<Applicant> findApplicantByNameAndEmail(String name, String email);

}
