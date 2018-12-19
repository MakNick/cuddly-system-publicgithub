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
import com.topicus.CFPApplication.domain.PresentationDraft;
import com.topicus.CFPApplication.domain.PresentationDraftApplicant;
import com.topicus.CFPApplication.persistence.PresentationDraftService;
import com.topicus.CFPApplication.persistence.SubscribeService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@Api(value = "PresentationdraftEndpoint", description = "Manipulate presentationdrafts")
public class PresentationDraftEndpoint {

	private PresentationDraftService presentationDraftService;

	private SubscribeService subscribeService;

	@Autowired
	public PresentationDraftEndpoint(PresentationDraftService presentationDraftService,
			SubscribeService subscribeService) {
		this.presentationDraftService = presentationDraftService;
		this.subscribeService = subscribeService;

	}

	@ApiOperation("Retrieves all available presentationdrafts from the database")
	@ApiResponses({ @ApiResponse(code = 200, message = "Successfully retrieved all presentationdrafts") })
	@GetMapping("api/presentationdraft")
	public ResponseEntity<Iterable<PresentationDraft>> listPresentationDrafts() {
		Iterable<PresentationDraft> presentationDrafts = presentationDraftService.findAll();
		return ResponseEntity.ok(presentationDrafts);
	}

	@ApiOperation("Adds a new presentationdraftapplicant. This object contains a presentationdraft and a list of applicants")
	@ApiResponses({ @ApiResponse(code = 200, message = "Successfully added a presentationdraftapplicant") })
	@PostMapping("api/presentationdraft")
	public ResponseEntity<PresentationDraft> save(
			@RequestBody @Valid PresentationDraftApplicant presentationDraftApplicant) {
		PresentationDraft presentationDraft = presentationDraftApplicant.getPresentationDraft();
		Set<Applicant> applicants = presentationDraftApplicant.getApplicants();
		subscribeService.linkPresentationDraftWithApplicants(presentationDraft, applicants);
		return ResponseEntity.ok(presentationDraft);
	}

	@ApiOperation("Retrieves a presentationdraft by ID")
	@ApiResponses({ @ApiResponse(code = 200, message = "Successfully retrieved a presentationdraft with the given ID"),
			@ApiResponse(code = 404, message = "Could not retrieve a presentationdraft with the given ID") })
	@GetMapping("api/presentationdraft/{id}")
	public ResponseEntity<PresentationDraft> findById(
			@ApiParam(required = true, name = "id", value = "Presentationdraft ID") @PathVariable("id") Long id) {
		Optional<PresentationDraft> result = this.presentationDraftService.findById(id);
		if (result.isPresent()) {
			return ResponseEntity.ok(result.get());
		} else {
			return ResponseEntity.status(404).build();
		}
	}

	@ApiOperation("Changes the label of the selected presentationdraft")
	@PostMapping("api/presentationdraft/{id}/label/{value}")
	public boolean changeLabel(
			@ApiParam(required = true, name = "id", value = "Presentationdraft ID") @PathVariable("id") Long id,
			@ApiParam(required = true, name = "value", value = "1. Denied 2. Accepted 3. Reserved 4. Undetermined") @PathVariable("value") Integer value) {
		return this.presentationDraftService.changeLabel(id, value);
	}

	@ApiOperation("Deletes a presentationdraft by ID")
	@ApiResponses({
			@ApiResponse(code = 200, message = "Successfully deleted the presentationdraft with the given ID") })
	@DeleteMapping("api/presentationdraft/delete/{id}")
	public ResponseEntity delete(
			@ApiParam(required = true, name = "id", value = "Presentationdraft ID") @PathVariable("id") Long id) {
		presentationDraftService.delete(id);
		return ResponseEntity.ok().build();
	}

	@ApiOperation("Retrieves all presentationdrafts with the given label value")
	@ApiResponses({
			@ApiResponse(code = 200, message = "Successfully retrieved all presentationdraft with the given label value") })
	@GetMapping("api/presentationdraft/findbylabel/{value}")
	public ResponseEntity<Iterable<PresentationDraft>> listPresentationDraftsByLabel(
			@ApiParam(required = true, name = "value", value = "1. Denied 2. Accepted 3. Reserved 4. Undetermined") @PathVariable("value") int value) {
		Iterable<PresentationDraft> presentationDraftsByLabel = presentationDraftService.findByLabel(value);
		return ResponseEntity.ok(presentationDraftsByLabel);
	}

	@ApiOperation("Finalize all presentationdrafts")
	@ApiResponses({ @ApiResponse(code = 200, message = "Successfully finalized all presentationdrafts"),
			@ApiResponse(code = 412, message = "Deadline has not yet passed, or there are still presentationdrafts with the label value of undetermined or unlabeled") })
	@GetMapping("api/presentationdraft/finalize")
	public ResponseEntity makePresentationDraftsFinal() {
		return presentationDraftService.makePresentationDraftsFinal();
	}

	@ApiOperation("Adds a presentationdraft")
	@PostMapping("api/presentationdraft/changepresentationdraft")
	public PresentationDraft changePresentationDraft(PresentationDraft presentationDraft) {
		return presentationDraftService.save(presentationDraft);
	}

}
