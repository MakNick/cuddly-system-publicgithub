package com.topicus.CFPApplication.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.topicus.CFPApplication.domain.PresentationDraft;
import com.topicus.CFPApplication.domain.PresentationDraft.Label;

@Repository
public interface PresentationDraftRepository extends PagingAndSortingRepository<PresentationDraft, Long> {

	Page<PresentationDraft> findPresentationDraftByConferenceIdAndLabelOrderBySubject(Long conferenceId, Label label, Pageable pageableRequest);

	Page<PresentationDraft> findPresentationDraftByConferenceIdAndCategoryOrderByLabelDesc(Long conferenceId, String category, Pageable pageableRequest);

	Page<PresentationDraft> findPresentationDraftByConferenceIdAndCategoryAndLabelOrderBySubject(Long conferenceId, String category, Label label, Pageable pageableRequest);

	Page<PresentationDraft> findPresentationDraftByConferenceIdOrderByLabelDesc(Long conferenceId, Pageable pageableRequest);

	Page<PresentationDraft> findPresentationDraftByConferenceIdAndSubjectContainingAndCategoryAndLabel(long conferenceId, String subject, String category,
																													   Label label, Pageable pageableRequest);

	Page<PresentationDraft> findPresentationDraftByConferenceIdAndSubjectContainingAndCategoryOrderByLabelDesc(long conferenceId, String subject, String category,
																													  Pageable pageableRequest);

	Page<PresentationDraft> findPresentationDraftByConferenceIdAndSubjectContainingAndLabel(long conferenceId, String subject, Label label, Pageable pageableRequest);

	Page<PresentationDraft> findPresentationDraftByConferenceIdAndSubjectContainingOrderByLabelDesc(long conferenceId, String subject, Pageable pageableRequest);


}
