package com.topicus.CFPApplication.api;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

	// Deze kan weg zodra we via een conference de presentationdrafts ophalen
	@ApiOperation("Retrieves all available presentationdrafts from the database")
	@ApiResponses({ @ApiResponse(code = 200, message = "Successfully retrieved all presentationdrafts") })
	@GetMapping("api/presentationdraft")
	public ResponseEntity<Object> listPresentationDrafts() {
		List<PresentationDraft> presentationDrafts = (List<PresentationDraft>) presentationDraftService.findAll();
		if (presentationDrafts.isEmpty()) {
			return new ResponseEntity<>("Presentationdraft list is empty", HttpStatus.NOT_FOUND);
		}
		return ResponseEntity.ok(presentationDrafts);
	}

	@ApiOperation("Adds a new presentationdraftapplicant. This object contains a presentationdraft and a list of applicants")
	@ApiResponses({ @ApiResponse(code = 200, message = "Successfully added a presentationdraftapplicant") })
	@PostMapping("api/presentationdraft")
	public ResponseEntity<PresentationDraft> save(
			@RequestBody @Valid PresentationDraftApplicant presentationDraftApplicant) {
		if (presentationDraftApplicant != null) {
			PresentationDraft presentationDraft = presentationDraftApplicant.getPresentationDraft();
			Set<Applicant> applicants = presentationDraftApplicant.getApplicants();
			subscribeService.linkPresentationDraftWithApplicants(presentationDraft, applicants);
			return ResponseEntity.ok(presentationDraft);
		}
		return ResponseEntity.badRequest().build();
	}

	@ApiOperation("Retrieves a presentationdraft by ID")
	@ApiResponses({ @ApiResponse(code = 200, message = "Successfully retrieved a presentationdraft with the given ID"),
			@ApiResponse(code = 404, message = "Could not retrieve a presentationdraft with the given ID") })
	@GetMapping("api/presentationdraft/{id}")
	public ResponseEntity<Object> findById(
			@ApiParam(required = true, name = "id", value = "Presentationdraft ID", type = "Long") @PathVariable("id") Long id) {
		if (id != null && id > 0) {
			Optional<PresentationDraft> result = this.presentationDraftService.findById(id);
			if (result.isPresent()) {
				return ResponseEntity.ok(result.get());
			} else {
				return new ResponseEntity<>("Could not find presentation with the given ID", HttpStatus.NOT_FOUND);
			}
		} else {
			return ResponseEntity.badRequest().build();
		}
	}

	@ApiOperation("Changes the label of the selected presentationdraft")
	@PostMapping("api/presentationdraft/{id}/label/{value}")
	public ResponseEntity<Object> changeLabel(
			@ApiParam(required = true, name = "id", value = "Presentationdraft ID", type = "Long") @PathVariable("id") Long id,
			@ApiParam(required = true, name = "value", value = "1. Denied 2. Accepted 3. Reserved 4. Undetermined", type = "Integer") @PathVariable("value") Integer value) {
		if (id != null && id > 0 && value != null && value > 0 && value <= 4) {
			int result = this.presentationDraftService.changeLabel(id, value);
			if (result == 0) {
				return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
			} else if (result == -1) {
				return new ResponseEntity<>("Presentationdraft with the given ID does not exist", HttpStatus.NOT_FOUND);
			} else {
				return ResponseEntity.ok().body(result);
			}
		}
		return new ResponseEntity<>("ID does not exist or label value is invalid", HttpStatus.BAD_REQUEST);

	}

	// tot hier
	@ApiOperation("Deletes a presentationdraft by ID")
	@ApiResponses({
			@ApiResponse(code = 200, message = "Successfully deleted the presentationdraft with the given ID") })
	@DeleteMapping("api/presentationdraft/delete/{id}")
	public ResponseEntity<Boolean> delete(
			@ApiParam(required = true, name = "id", value = "Presentationdraft ID", type = "Long") @PathVariable("id") Long id) {
		presentationDraftService.delete(id);
		return ResponseEntity.ok(true);
	}

	@ApiOperation("Retrieves all presentationdrafts with the given label value")
	@ApiResponses({
			@ApiResponse(code = 200, message = "Successfully retrieved all presentationdraft with the given label value") })
	@GetMapping("api/presentationdraft/findbylabel/{value}")
	public ResponseEntity<Iterable<PresentationDraft>> listPresentationDraftsByLabel(
			@ApiParam(required = true, name = "value", value = "1. Denied 2. Accepted 3. Reserved 4. Undetermined", type = "Integer") @PathVariable("value") Integer value) {
		Iterable<PresentationDraft> presentationDraftsByLabel = presentationDraftService.findByLabel(value);
		return ResponseEntity.ok(presentationDraftsByLabel);
	}

	@ApiOperation("Finalize all presentationdrafts")
	@ApiResponses({ @ApiResponse(code = 200, message = "Successfully finalized all presentationdrafts"),
			@ApiResponse(code = 412, message = "Deadline has not yet passed, or there are still presentationdrafts with the label value of undetermined or unlabeled") })
	@GetMapping("api/presentationdraft/finalize")
	public ResponseEntity<Object> makePresentationDraftsFinal() {
		return presentationDraftService.makePresentationDraftsFinal();
	}

	@ApiOperation("Adds a presentationdraft")
	@PostMapping("api/presentationdraft/changepresentationdraft")
	public PresentationDraft changePresentationDraft(PresentationDraft presentationDraft) {
		return presentationDraftService.save(presentationDraft);
	}

}
