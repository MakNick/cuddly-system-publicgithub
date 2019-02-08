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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.topicus.CFPApplication.domain.Applicant;
import com.topicus.CFPApplication.domain.Conference;
import com.topicus.CFPApplication.domain.PresentationDraft;
import com.topicus.CFPApplication.domain.PresentationDraftApplicant;
import com.topicus.CFPApplication.persistence.ConferenceService;
import com.topicus.CFPApplication.persistence.RequestCategorizedDraftsService;
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

	private RequestCategorizedDraftsService requestCategorizedDraftsService;

	@Autowired
	public ConferenceEndpoint(ConferenceService conferenceService, SubscribeService subscribeService,
			RequestCategorizedDraftsService requestCategorizedDraftsService) {
		this.conferenceService = conferenceService;
		this.subscribeService = subscribeService;
		this.requestCategorizedDraftsService = requestCategorizedDraftsService;
	}

	@ApiOperation("Retrieves all available conference from the database")
	@ApiResponses({ @ApiResponse(code = 200, message = "Successfully retrieved all conferences"),
			@ApiResponse(code = 404, message = "No conferences could be found") })
	@GetMapping("api/conference")
	public ResponseEntity<Iterable<Conference>> conferenceList() {
		List<Conference> result = (List<Conference>) conferenceService.findAll();
		if (!result.isEmpty()) {
			return ResponseEntity.ok().body(result);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}

	@ApiOperation("Retrieves a conference by ID")
	@ApiResponses({ @ApiResponse(code = 200, message = "Successfully retrieved a conference with the given ID"),
			@ApiResponse(code = 404, message = "Could not retrieve a conference with the given ID"),
			@ApiResponse(code = 400, message = "Invalid ID value") })
	@GetMapping("api/conference/{id}")
	public ResponseEntity<Conference> getConferenceById(
			@ApiParam(required = true, name = "id", value = "Conference ID", type = "Long") @PathVariable("id") Long id) {
		if (id != null && id > 0) {
			Optional<Conference> result = conferenceService.findById(id);
			if (result.isPresent()) {
				return ResponseEntity.ok(result.get());
			}
			return ResponseEntity.status(404).build();
		}
		return ResponseEntity.badRequest().build();
	}

	@ApiOperation(value = "Adds a new conference")
	@ApiResponses({ @ApiResponse(code = 200, message = "Successfully added a conference"),
			@ApiResponse(code = 400, message = "Invalid conference object") })
	@PostMapping("api/conference")
	public ResponseEntity<Conference> saveConference(@RequestBody @Valid Conference conference) {
		if (conference != null) {
			return ResponseEntity.ok(conferenceService.save(conference));
		}
		
		return ResponseEntity.badRequest().build();
	}

	@ApiOperation(value = "Adds a new presentation at he given conference ID")
	@ApiResponses({ @ApiResponse(code = 200, message = "Successfully added a new conference"),
			@ApiResponse(code = 404, message = "Conference with the given ID doest not exist"),
			@ApiResponse(code = 400, message = "ID value or presentationdraftapplicant is invalid") })
	@PostMapping("api/conference/{id}/savepresentationdraft")
	public ResponseEntity<Object> savePresentationDraftInConference(
			@ApiParam(required = true, name = "id", value = "Conference ID") @PathVariable("id") Long id,
			@RequestBody @Valid PresentationDraftApplicant presentationDraftApplicant) {
		if (id != null && id > 0 && presentationDraftApplicant != null) {
			PresentationDraft presentationDraft = presentationDraftApplicant.getPresentationDraft();
			Set<Applicant> applicants = presentationDraftApplicant.getApplicants();
			presentationDraft = subscribeService.linkPresentationDraftWithApplicants(presentationDraft, applicants);
			Optional<Conference> result = conferenceService.findById(id);
			if (result.isPresent()) {
				return ResponseEntity
						.ok(subscribeService.linkPresentationDraftWithConference(result.get(), presentationDraft));
			}
			return new ResponseEntity<>("Conference with the given ID does not exist", HttpStatus.NOT_FOUND);
		}
		return ResponseEntity.badRequest().build();
	}

	@ApiOperation("Deletes a conference by ID")
	@ApiResponses({ @ApiResponse(code = 200, message = "Successfully deleted the conference with the given ID"),
			@ApiResponse(code = 400, message = "Invalid ID value") })
	@DeleteMapping("api/conference/delete/{id}")
	public ResponseEntity<Conference> delete(
			@ApiParam(required = true, name = "id", value = "Conference ID") @PathVariable("id") Long id) {
		if (id != null && id > 0) {
			try {
				conferenceService.delete(id);
				return ResponseEntity.ok().build();
			} catch (Exception e) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			}
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	}

	@ApiOperation("Retrieves Presentationdrafts for a conference of a certain label. 0=unlabeled, 1=denied, 2=accepted, 3=reserved, 4=undetermined, anyOtherNumber=all")
	@GetMapping("api/conference/{id}/findpresentationdraft/{label}")
	public ResponseEntity<Iterable<PresentationDraft>> findPresentationdrafts(
			@ApiParam(required = true, name = "id", value = "Conference ID") @PathVariable("id") Long id,
			@ApiParam(required = true, name = "label", value = "Label value: 0. Unlabeled 1. Denied 2. Accepted 3. Reserved 4. Undetermined 5. All") @PathVariable("label") int label) {
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

	@ApiOperation("Retrieves Presentationdrafts for a conference of a certain category. If a non-existing category is passed, then all categories will be shown.")
	@ApiResponses({@ApiResponse(code = 200, message = "Successfully retrieved the presentationDrafts with a certain category"), 
		@ApiResponse(code = 404, message = "The conference passed does not exist"),
		@ApiResponse(code = 400, message = "The conference or category passed is invalid"),
		@ApiResponse(code = 417, message = "An unexpected situation occured.")})
	@GetMapping(path = "api/findpresentationdraftsbycategory")
	public ResponseEntity<Iterable<PresentationDraft>> findPresentationdraftsByCategory(
			@RequestParam(value = "id", required = true) Long id,
			@RequestParam(value = "category", required = true) String category) {
		if (category != null && id != null && id > 0) {
			category = category.substring(1, category.length() - 1);
			Optional<Conference> conference = conferenceService.findById(id);
			if (conference.isPresent()) {
				if (conference.get().getCategories().contains(category)) {
					Iterable<PresentationDraft> result = requestCategorizedDraftsService
							.findPresentationDraftsByCategory(conference.get(), category);
					return ResponseEntity.ok(result);
				} else if (category.equals("Toon alle")) {
					Iterable<PresentationDraft> result = conferenceService.findPresentationDrafts(conference.get(), 5);
					return ResponseEntity.ok(result);
				} else {
					return ResponseEntity.status(417).build();
				}
			}
			return ResponseEntity.status(404).build();
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	}

}
