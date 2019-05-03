package com.topicus.CFPApplication.api;

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

	/*
	 *  TODO: Methode moet nog geïmplementeerd en opgeschoond worden.
	 */
//	@ApiOperation("Finalize all presentationdrafts")
//	@ApiResponses({ @ApiResponse(code = 200, message = "Successfully finalized all presentationdrafts"),
//			@ApiResponse(code = 412, message = "Deadline has not yet passed, or there are still presentationdrafts with the label value of: undetermined or unlabeled"),
//			@ApiResponse(code = 404, message = "Invalid conference ID"),
//			@ApiResponse(code = 418, message = "Already finalized presentationDrafts") })
//	@GetMapping("api/{conferenceId}/presentationdraft/finalize")
//	public ResponseEntity<Object> makePresentationDraftsFinal(
//			@ApiParam(required = true, name = "conferenceId", value = "Conference ID") @PathVariable("conferenceId") Long conferenceId) {
//		if (conferenceId > 0 && conferenceId != null) {
//			try {
//				if (!conferenceService.findById(conferenceId).get().getPresentations().isEmpty()) {
//					return new ResponseEntity<>("Already finalized presentationDrafts", HttpStatus.I_AM_A_TEAPOT);
//				} else {
//					for (int label = 1; label < 4; label++) {
//						List<PresentationDraft> listPresentationDrafts = presentationDraftService
//								.makePresentationDraftsFinal(conferenceId, label);
//						if (label == 2) {
//							presentationService.makePresentation((ArrayList<PresentationDraft>) listPresentationDrafts,
//									conferenceService.findById(conferenceId).get());
//						}
//						for (PresentationDraft draft : listPresentationDrafts) {
//							List<Applicant> listApplicants = new ArrayList<>(draft.getApplicants());
//							for (Applicant applicant : listApplicants) {
//								mailService.sendMailText(applicant.getEmail(), applicant.getName(),
//										"MailTemplate " + draft.getLabel().toString()); // GETNAME CONFERENCE AANPASSEN NAAR
//																				// GETTEMPLATE TEXT VANUIT INNERCLASS IN
//																				// CONFERENCE!
//							}
//						}
//					}
//					return new ResponseEntity<>("Finalize mails sent (Accepted, reserved and denied)", HttpStatus.OK);
//				}
//			} catch (CannotProceedException cpe) {
//				return new ResponseEntity<>("Deadline not passed or Unlabeled presentationDrafts not empty",
//						HttpStatus.PRECONDITION_FAILED);
//			} catch (NoSuchElementException nse) {
//				return new ResponseEntity<>("Conference not found", HttpStatus.NOT_FOUND);
//			}
//		}
//		return new ResponseEntity<>("Invalid conference ID", HttpStatus.NOT_FOUND);
//	}
}
