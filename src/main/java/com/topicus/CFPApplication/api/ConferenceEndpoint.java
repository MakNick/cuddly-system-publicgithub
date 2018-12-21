package com.topicus.CFPApplication.api;

import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.topicus.CFPApplication.domain.Applicant;
import com.topicus.CFPApplication.domain.Conference;
import com.topicus.CFPApplication.domain.PresentationDraft;
import com.topicus.CFPApplication.domain.PresentationDraftApplicant;
import com.topicus.CFPApplication.persistence.ConferenceService;
import com.topicus.CFPApplication.persistence.SubscribeService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@Api(value = "ConferenceEndpoint", description = "Manipulate conferences")
public class ConferenceEndpoint {

	private ConferenceService conferenceService;

	private SubscribeService subscribeService;

	@Autowired
	public ConferenceEndpoint(ConferenceService conferenceService, SubscribeService subscribeService) {
		this.conferenceService = conferenceService;
		this.subscribeService = subscribeService;
	}

	@ApiOperation("Retrieves all available conference from the database")
	@ApiResponses({ @ApiResponse(code = 200, message = "Successfully retrieved all conferences") })
	@GetMapping("api/conference")
	public ResponseEntity<Iterable<Conference>> conferenceList() {
		Iterable<Conference> result = conferenceService.findAll();
		return ResponseEntity.ok(result);
	}

	@ApiOperation("Retrieves a conference by ID")
	@ApiResponses({ @ApiResponse(code = 200, message = "Successfully retrieved a conference with the given ID"),
			@ApiResponse(code = 404, message = "Could not retrieve a conference with the given ID") })
	@GetMapping("api/conference/{id}")
	public ResponseEntity<Conference> getConferenceById(
			@ApiParam(required = true, name = "id", value = "Conference ID", type="Long") @PathVariable("id") Long id) {
		Optional<Conference> result = conferenceService.findById(id);
		if (result.isPresent()) {
			return ResponseEntity.ok(result.get());
		}
		return ResponseEntity.status(404).build();
	}
	
	@ApiOperation("Adds a new conference")
	@ApiResponses({ @ApiResponse(code = 200, message = "Successfully added a conference") })
	@PostMapping("api/conference")
	public ResponseEntity<Conference> saveConference(@RequestBody @Valid Conference conference) {
		return ResponseEntity.ok(conferenceService.save(conference));
	}

	@PostMapping("api/conference/{id}/savepresentationdraft")
	public ResponseEntity<Conference> savePresentationDraftInConference(@PathVariable("id") Long id,
			@RequestBody @Valid PresentationDraftApplicant presentationDraftApplicant) {
		PresentationDraft presentationDraft = presentationDraftApplicant.getPresentationDraft();
		Set<Applicant> applicants = presentationDraftApplicant.getApplicants();
		presentationDraft = subscribeService.linkPresentationDraftWithApplicants(presentationDraft, applicants);
		Optional<Conference> result = conferenceService.findById(id);
		return ResponseEntity.ok(subscribeService.linkPresentationDraftWithConference(result.get(), presentationDraft));
	}
	
	@ApiOperation("Deletes a conference by ID")
	@ApiResponses({
			@ApiResponse(code = 200, message = "Successfully deleted the conference with the given ID") })
	@DeleteMapping("api/conference/delete/{id}")
	public ResponseEntity delete(
			@ApiParam(required = true, name = "id", value = "Conference ID") @PathVariable("id") Long id) {
		conferenceService.delete(id);
		return ResponseEntity.ok().build();
	}
	
	
	
	@ApiOperation("Retrieves Presentationdrafts for a conference of a certain label. 0=unlabeled, 1=denied, 2=accepted, 3=reserved, 4=undetermined, anyOtherNumber=all")
	
	@GetMapping("api/conference/{id}/findpresentationdraft/{label}")
	public ResponseEntity<Iterable<PresentationDraft>> findPresentationdrafts(@PathVariable("id") Long id, @PathVariable("label") int label) {
		Optional<Conference> conference = conferenceService.findById(id);
		if (conference.isPresent()) {
			if (label <= 5 && label >= 0) {
				Iterable<PresentationDraft> result = conferenceService.findPresentationDrafts(conference.get(), label);
				return ResponseEntity.ok(result);
			} else {
				return ResponseEntity.status(417).build();
			}	
		} else {
		return ResponseEntity.status(404).build();
		}
	}
	
	
	
	

}
