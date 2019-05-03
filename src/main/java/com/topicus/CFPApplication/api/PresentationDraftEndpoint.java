package com.topicus.CFPApplication.api;

import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
@Api(value = "PresentationDraftEndpoint", description = "Manipulate presentationdrafts")
public class PresentationDraftEndpoint {

	private PresentationDraftService presentationDraftService;
	private SubscribeService subscribeService;

	@Autowired
	public PresentationDraftEndpoint(PresentationDraftService presentationDraftService,
			SubscribeService subscribeService) {
		this.presentationDraftService = presentationDraftService;
		this.subscribeService = subscribeService;
	}

	@ApiOperation(value = "Adds a new presentation draft. This object contains a presentationdraft and a list of applicants")
	@ApiResponses({ @ApiResponse(code = 200, message = "Successfully added a presentationdraft and the host") })
	@PostMapping("api/presentationdraft")
	public ResponseEntity<PresentationDraft> save(
			@RequestBody @Valid PresentationDraftApplicant presentationDraftApplicant) {
		if (presentationDraftApplicant != null) {
			PresentationDraft presentationDraft = presentationDraftApplicant.getPresentationDraft();
			Set<Applicant> applicants = presentationDraftApplicant.getApplicants();
			return ResponseEntity
					.ok(subscribeService.linkPresentationDraftWithApplicants(presentationDraft, applicants));
		}
		return ResponseEntity.badRequest().build();
	}

	@ApiOperation("Looks for a draft based on the searchPresentationDrafts criteria's")
	@ApiResponses({ @ApiResponse(code = 200, message = "Successfully retrieved a presentation draft with the given ID"),
			@ApiResponse(code = 404, message = "Could not retrieve a presentation draft with the given criteria's") })
	@GetMapping("api/presentationdraft/{id}")
	public ResponseEntity<?> findById(
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
	@ApiResponses({
			@ApiResponse(code = 200, message = "Successfully changed the label of the conference with the given ID"),
			@ApiResponse(code = 400, message = "Invalid ID or label value"),
			@ApiResponse(code = 404, message = "Could not find a presentationdraft with the given ID"),
			@ApiResponse(code = 304, message = "This label has already been assigned to this presentationdraft") })
	@PostMapping("api/presentationdraft/{id}/label/{value}")
	public ResponseEntity<?> changeLabel(
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

	@ApiOperation("Deletes a presentationdraft by ID")
	@ApiResponses({ @ApiResponse(code = 200, message = "Successfully deleted the presentationdraft with the given ID"),
			@ApiResponse(code = 400, message = "Invalid ID value given"),
			@ApiResponse(code = 404, message = "Could not find presentationdraft with the given ID") })
	@DeleteMapping("api/presentationdraft/delete/{id}")
	public ResponseEntity<Boolean> delete(
			@ApiParam(required = true, name = "id", value = "Presentationdraft ID", type = "Long") @PathVariable("id") Long id) {
		if (id > 0 && id != null) {
			if (presentationDraftService.delete(id)) {
				return ResponseEntity.ok(true);
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(false);
			}
		}
		return ResponseEntity.badRequest().build();
	}

	@ApiOperation(value = "Adds a presentationdraft", hidden = true)
	@PutMapping("api/save_presentationdraft/conferenceId/{id}")
	public PresentationDraft changePresentationDraft(
			@PathVariable("id") long conferenceId,
			@RequestBody @Valid PresentationDraft presentationDraft) {
		return presentationDraftService.save(conferenceId, presentationDraft);
	}

}
