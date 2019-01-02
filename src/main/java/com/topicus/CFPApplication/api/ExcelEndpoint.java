package com.topicus.CFPApplication.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.topicus.CFPApplication.persistence.ExcelService;

import io.swagger.annotations.ApiParam;

@RestController
public class ExcelEndpoint {

	private ExcelService excelService;

	@Autowired
	public ExcelEndpoint(ExcelService excelService) {
		this.excelService = excelService;
	}

	@GetMapping("api/{conferenceId}/excel")
	public ResponseEntity<Object> createExcel(
			@ApiParam(required = true, name = "conferenceId", value = "Conference ID") @PathVariable("conferenceId") Long conferenceId) {
		excelService.createExcel(conferenceId);
		return ResponseEntity.ok().build();

//		if (result == 1) {
//			return ResponseEntity.ok().build();
//		} else if (result == 2) {
//			return new ResponseEntity<>("Interrupted I/O operation.", HttpStatus.CONFLICT);
//		} else {
//			return new ResponseEntity<>("No presentationdrafts availiable", HttpStatus.NOT_FOUND);
//		}
	}

}
