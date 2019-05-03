package com.topicus.CFPApplication.persistence.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import com.topicus.CFPApplication.domain.Conference;

@Component
public interface ConferenceRepository extends CrudRepository<Conference, Long> {
	
	Iterable<Conference> findAllByOrderByName();

}
