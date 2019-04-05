package com.topicus.CFPApplication.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.topicus.CFPApplication.domain.PresentationDraft;
import com.topicus.CFPApplication.domain.PresentationDraft.Label;

@Repository
public interface PresentationDraftRepository extends PagingAndSortingRepository<PresentationDraft, Long> {

	@Query(value = "select * from presentation_draft p where p.conference_id = :conferenceId and "
			+ "(:label is null or p.label = :label) and"
			+ "(:subject is null or p.subject like %:subject%) and"
			+ "(:category is null or p.category = :category)", nativeQuery = true)
	Page<PresentationDraft> searchPresentationDrafts(
			@Param("conferenceId") long conferenceId,
			@Param("subject") String subject,
			@Param("category") String category,
			@Param("label") String label, Pageable pageableRequest);

	Page<PresentationDraft> findPresentationDraftByConferenceIdAndLabelOrderBySubject(Long conferenceId, Label label, Pageable pageableRequest);

	Page<PresentationDraft> findPresentationDraftByConferenceIdOrderByLabelDesc(Long conferenceId, Pageable pageableRequest);


}
