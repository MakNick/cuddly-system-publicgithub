package com.topicus.CFPApplication.persistence.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import com.topicus.CFPApplication.domain.Presentation;

@Component
public interface PresentationRepository extends CrudRepository<Presentation, Long>{

}
