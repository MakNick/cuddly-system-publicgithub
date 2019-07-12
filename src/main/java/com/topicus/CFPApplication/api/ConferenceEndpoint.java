package com.topicus.CFPApplication.api;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;

import com.topicus.CFPApplication.persistence.PresentationDraftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.topicus.CFPApplication.domain.Applicant;
import com.topicus.CFPApplication.domain.PresentationDraft;
import com.topicus.CFPApplication.domain.PresentationDraftApplicant;
import com.topicus.CFPApplication.domain.conference.Conference;
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
    private PresentationDraftService presentationDraftService;
    private SubscribeService subscribeService;

    @Autowired
    public ConferenceEndpoint(ConferenceService conferenceService, SubscribeService subscribeService, PresentationDraftService presentationDraftService) {
        this.conferenceService = conferenceService;
        this.presentationDraftService = presentationDraftService;
        this.subscribeService = subscribeService;
    }
    
    @ApiOperation(value = "Adds a new conference")
    @ApiResponses({@ApiResponse(code = 200, message = "Successfully added a conference"),
            @ApiResponse(code = 400, message = "Invalid conference object")})
    @PostMapping("api/conference")
    public ResponseEntity<Conference> saveConference(@RequestBody @Valid Conference conference) {
        if (conference != null) {
            return ResponseEntity.ok(conferenceService.save(conference));
        }

        return ResponseEntity.badRequest().build();
    }
    
    @ApiOperation("Deletes a conference by ID")
    @ApiResponses({@ApiResponse(code = 200, message = "Successfully deleted the conference with the given ID"),
            @ApiResponse(code = 400, message = "Invalid ID value")})
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

    @ApiOperation("Retrieves all available conference from the database")
    @ApiResponses({@ApiResponse(code = 200, message = "Successfully retrieved all conferences"),
            @ApiResponse(code = 404, message = "No conferences could be found")})
    @GetMapping("api/conference")
    public ResponseEntity<Iterable<Conference>> conferenceList() {
        List<Conference> result = (List<Conference>) conferenceService.findAll();
        if (!result.isEmpty()) {
            return ResponseEntity.ok().body(result);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @ApiOperation("Retrieves a conference by ID")
    @ApiResponses({@ApiResponse(code = 200, message = "Successfully retrieved a conference with the given ID"),
            @ApiResponse(code = 404, message = "Could not retrieve a conference with the given ID"),
            @ApiResponse(code = 400, message = "Invalid ID value")})
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
    
    @ApiOperation("Retrieves all presentation draft from a conference")
    @ApiResponses({@ApiResponse(code = 200, message = "Successfully retrieved the presentationDrafts with the given ID"),
            @ApiResponse(code = 404, message = "The conference passed does not exist")})
    @GetMapping(path = "api/conference/{id}/presentationdrafts")
    public ResponseEntity<Page<PresentationDraft>> getAllPresentationDrafts(
            @PathVariable(value = "id") Long id,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "limit", defaultValue = "25") Integer limit) {

        if (id > 0) {
            return ResponseEntity.ok().body(this.presentationDraftService.findAllByConferenceId(id, page, limit));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @ApiOperation("Retrieves all available presentation drafts from a conference, based on the searchcriteria")
    @ApiResponses({@ApiResponse(code = 200, message = "Successfully retrieved all drafts based on the searchcriteria"),
            @ApiResponse(code = 404, message = "No drafts were found with the current searchcriteria")})
    @GetMapping("api/conference/{id}/presentationdrafts/search")
    public ResponseEntity<Page<PresentationDraft>> searchPresentationDrafts(
            @PathVariable("id") long conferenceID,
            @RequestParam("s") String subject,
            @RequestParam("c") String category,
            @RequestParam("l") int labelId,
            @RequestParam("page") int page,
            @RequestParam("limit") int limit) {
        return ResponseEntity.ok(this.presentationDraftService.findAllBySearchCriteria(conferenceID, subject, category, labelId, page, limit));

    }

    @ApiOperation(value = "Adds a new presentation to the given conference ID")
    @ApiResponses({@ApiResponse(code = 200, message = "Successfully added a new conference"),
            @ApiResponse(code = 404, message = "Conference with the given ID doest not exist"),
            @ApiResponse(code = 400, message = "ID value or presentationdraftapplicant is invalid")})
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
}
