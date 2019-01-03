package com.topicus.CFPApplication.api;

import java.io.IOException;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.topicus.CFPApplication.persistence.ExcelService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@Api(value = "ExcelEndpoint", description = "Create Excel spreadsheets")
public class ExcelEndpoint {

	private ExcelService excelService;

	@Autowired
	public ExcelEndpoint(ExcelService excelService) {
		this.excelService = excelService;
	}

	@ApiOperation("Creates Excel spreadsheet from specified conference ID")
	@ApiResponses({ @ApiResponse(code = 200, message = "Successfully created Excel spreadsheet"),
			@ApiResponse(code = 404, message = "Conference and/or conference ID not found"),
			@ApiResponse(code = 412, message = "Save request was cancelled or shut down"), })
	@GetMapping("api/{conferenceId}/excel")
	public ResponseEntity<Object> createExcel(
			@ApiParam(required = true, name = "conferenceId", value = "Conference ID") @PathVariable("conferenceId") Long conferenceId) {
		if (conferenceId > 0 && conferenceId != null) {
			try {
				excelService.createExcel(conferenceId);
				return new ResponseEntity<>("Save completed", HttpStatus.OK);
			} catch (NoSuchElementException nsee) {
				return new ResponseEntity<>("Conference and/or conference ID not found", HttpStatus.NOT_FOUND);
			} catch (IOException e) {
				return new ResponseEntity<>("Cancelled save request", HttpStatus.PRECONDITION_FAILED);
			}
		}
		return new ResponseEntity<>("Invalid conference ID", HttpStatus.NOT_FOUND);
	}

}
