package com.topicus.CFPApplication.api;

import java.util.Optional;

import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.topicus.CFPApplication.domain.Applicant;
import com.topicus.CFPApplication.persistence.ApplicantService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@Api(value = "ApplicantEndpoint", description = "Manipulate applicants")
public class ApplicantEndpoint {

	private ApplicantService applicantService;

	@Autowired
	public ApplicantEndpoint(ApplicantService applicantService) {
		this.applicantService = applicantService;
	}

	@ApiOperation("Retrieves all available applicants from the database")
	@ApiResponses({ @ApiResponse(code = 200, message = "Successfully retrieved all applicants") })
	@GetMapping("api/applicant")
	public ResponseEntity<Iterable<Applicant>> listApplicants() {
		Iterable<Applicant> applicants = applicantService.findAll();
		return ResponseEntity.ok(applicants);
	}

	@ApiOperation("Retrieves an applicant by ID.")
	@ApiResponses({ @ApiResponse(code = 200, message = "Successfully retrieved an applicant with the given ID"),
			@ApiResponse(code = 404, message = "Could not retrieve an applicant with the given ID") })
	@GetMapping("api/applicant/{id}")
	public ResponseEntity<Applicant> findById(
			@ApiParam(required = true, name = "id", value = "Applicant ID") @PathVariable("id") Long id) {
		Optional<Applicant> result = this.applicantService.findById(id);
		if (result.isPresent()) {
			return ResponseEntity.ok(result.get());
		} else {
			return ResponseEntity.status(404).build();
		}
	}
}
