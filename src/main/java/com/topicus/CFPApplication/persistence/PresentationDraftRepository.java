package com.topicus.CFPApplication.persistence;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.topicus.CFPApplication.domain.PresentationDraft;
import com.topicus.CFPApplication.domain.PresentationDraft.Label;

@Repository
public interface PresentationDraftRepository extends CrudRepository<PresentationDraft, Long> {

//	@Query("SELECT COUNT(*) FROM PresentationDraft WHERE NOT (label='DENIED' OR label='ACCEPTED' OR label='RESERVED')")
//	public int countPresentationsUndetermined();

	public Iterable<PresentationDraft> findPresentationDraftByLabel(Label label);

	public Iterable<PresentationDraft> findPresentationDraftByCategory(String category);

}
