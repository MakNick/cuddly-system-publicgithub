package com.topicus.CFPApplication.api;

import java.awt.print.PrinterException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.topicus.CFPApplication.persistence.PdfService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@Api(value = "PdfEndpoint", description = "Create PDF file")
public class PdfEndpoint {

	private PdfService pdfService;

	@Autowired
	public PdfEndpoint(PdfService pdfService) {
		this.pdfService = pdfService;
	}

	@ApiOperation(value = "Get all presentationDrafts and create PDF")
	@ApiResponses({ @ApiResponse(code = 200, message = "Create PDF succesfully"),
			@ApiResponse(code = 404, message = "No presentationdraft available"),
			@ApiResponse(code = 412, message = "Cancelled save request") })
	@GetMapping("api/download/pdf")
	public ResponseEntity<Object> createPDF() {
		int result = pdfService.getPresentationDraftsToPDF();
		if (result == 1) {
			return ResponseEntity.ok().build();
		} else if (result == 2) {
			return new ResponseEntity<>("Save request was cancelled", HttpStatus.CONFLICT);
		} else {
			return new ResponseEntity<>("No presentationdrafts available", HttpStatus.NOT_FOUND);
		}
	}

	@ApiOperation(value = "Get single presentationDraft and create PDF ")
	@ApiResponses({ @ApiResponse(code = 200, message = "Create PDF succesfully"),
			@ApiResponse(code = 404, message = "No presentationdraft ID available"),
			@ApiResponse(code = 412, message = "Cancelled save request") })
	@GetMapping("api/download/pdf/{id}")
	public ResponseEntity<Object> getPresentationDraft(
			@ApiParam(required = true, name = "id", value = "PresentationDraft ID") @PathVariable("id") Long id) {
		int result = pdfService.getPresentationDraftToPDF(id);
		if (result == 1) {
			return ResponseEntity.ok().build();
		} else if (result == 2) {
			return new ResponseEntity<>("Save request was cancelled", HttpStatus.CONFLICT);
		} else {
			return new ResponseEntity<>("Could not find presentationdraft with the given ID", HttpStatus.NOT_FOUND);
		}
	}

	@ApiOperation(value = "Print all presentationDrafts")
	@ApiResponses({ @ApiResponse(code = 200, message = "Printing PDF succesfully"),
			@ApiResponse(code = 404, message = "No presentationdraft available"),
			@ApiResponse(code = 412, message = "Cancelled print request") })
	@GetMapping("api/print/pdf")
	public ResponseEntity<Object> printAllPdf() {
		int result;
		try {
			result = pdfService.printAllPdf();
			if (result == 1) {
				return ResponseEntity.ok().build();
			} else if (result == 2) {
				return new ResponseEntity<>("Interrupted I/O operation.", HttpStatus.CONFLICT);
			} else {
				return new ResponseEntity<>("No presentationdrafts available", HttpStatus.NOT_FOUND);
			}
		} catch (PrinterException e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>("Print job was cancelled", HttpStatus.NOT_FOUND);
	}

	@ApiOperation(value = "Print single presentationDraft")
	@ApiResponses({ @ApiResponse(code = 200, message = "Printing PDF succesfully"),
			@ApiResponse(code = 404, message = "No presentationdraft ID available"),
			@ApiResponse(code = 412, message = "Cancelled print request") })
	@GetMapping("api/print/pdf/{id}")
	public ResponseEntity<Object> printSinglePdf(
			@ApiParam(required = true, name = "id", value = "PresentationDraft ID") @PathVariable("id") Long id) {
		int result;
		try {
			result = pdfService.printSinglePdf(id);
			if (result == 1) {
				return ResponseEntity.ok().build();
			} else if (result == 2) {
				return new ResponseEntity<>("Interrupted I/O operation.", HttpStatus.CONFLICT);
			} else {
				return new ResponseEntity<>("Could not find presentationdraft ID", HttpStatus.NOT_FOUND);
			}
		} catch (PrinterException e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>("Print job was cancelled", HttpStatus.NOT_FOUND);
	}
}
