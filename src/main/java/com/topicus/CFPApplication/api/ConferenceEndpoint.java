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
    private PresentationDraftService presentationDraftService;
    private SubscribeService subscribeService;

    @Autowired
    public ConferenceEndpoint(ConferenceService conferenceService, SubscribeService subscribeService, PresentationDraftService presentationDraftService) {
        this.conferenceService = conferenceService;
        this.presentationDraftService = presentationDraftService;
        this.subscribeService = subscribeService;
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

    @ApiOperation(value = "Adds a new presentation at he given conference ID")
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

    @ApiOperation("Retrieves presentationdrafts from a conference with a certain label. 0=unlabeled, 1=denied, 2=accepted, 3=reserved, 4=undetermined, anyOtherNumber=all")
    @GetMapping("api/conference/{conferenceId}/presentationdrafts/label/{labelId}")
    public ResponseEntity<Page<PresentationDraft>> findPresentationdrafts(
            @ApiParam(required = true, name = "conferenceId", value = "Conference ID") @PathVariable("conferenceId") Long conferenceId,
            @ApiParam(required = true, name = "labelId", value = "Label numeric value") @PathVariable("labelId") byte labelId,
            int page, int limit) {
        if (labelId <= 5 && labelId >= 0) {
            try {
                return ResponseEntity.ok(presentationDraftService.findPresentationDraftsByLabel(conferenceId, labelId, page, limit));
            }catch(RuntimeException re){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).varyBy("Could not find any presentation draft with the given label").build();
            }
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @ApiOperation("Retrieves presentationdrafts from a conference with a certain category")
    @GetMapping("api/conference/{conferenceId}/presentationdrafts/category/{category}")
    public ResponseEntity<Page<PresentationDraft>> findPresentationdrafts(
            @ApiParam(required = true, name = "conferenceId", value = "Conference ID") @PathVariable("conferenceId") Long conferenceId,
            @ApiParam(required = true, name = "category", value = "Category value") @PathVariable("category") String category,
            int page, int limit) {
        if (category != null && category.length() > 0) {
            try {
                return ResponseEntity.ok(presentationDraftService.findPresentationDraftsByCategory(conferenceId, category, page, limit));
            }catch(RuntimeException re){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).varyBy("Could not find any presentation draft with the given category").build();
            }
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @ApiOperation("Retrieves presentationdrafts from a conference with a certain category and label")
    @GetMapping("api/conference/{conferenceId}/presentationdrafts/category/{category}/labelId/{labelId}")
    public ResponseEntity<Page<PresentationDraft>> findPresentationdrafts(
            @ApiParam(required = true, name = "conferenceId", value = "Conference ID") @PathVariable("conferenceId") Long conferenceId,
            @ApiParam(required = true, name = "category", value = "Category value") @PathVariable("category") String category,
            @ApiParam(required = true, name = "labelId", value = "label numeric value") @PathVariable("labelId") byte labelId,
            int page, int limit) {
        if (category != null && category.length() > 0 && labelId <= 5 && labelId >= 0) {
            try {
                return ResponseEntity.ok(presentationDraftService.findPresentationDraftsByCategoryAndLabel(conferenceId, category, labelId, page, limit));
            }catch(RuntimeException re){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).varyBy("Could not find any presentation drafts with the given category and label combination").build();
            }
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @ApiOperation("Retrieves all presentationdraft from a conference.")
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

}
