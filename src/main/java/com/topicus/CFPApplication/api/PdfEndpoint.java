package com.topicus.CFPApplication.api;

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

@RestController
@Api(value = "PDFEndpoint", description = "create PDF", hidden = true)
public class PdfEndpoint {

	private PdfService pdfService;

	@Autowired
	public PdfEndpoint(PdfService pdfService) {
		this.pdfService = pdfService;
	}

	@ApiOperation(value = "Get all presentationDrafts and create PDF", hidden = true)
	@GetMapping("api/pdf")
	public ResponseEntity<Object> createPDF() {
		int result = pdfService.getPresentationDraftsToPDF();
		if (result == 1) {
			return ResponseEntity.ok().build();
		} else if (result == 2) {
			return new ResponseEntity<>("Interrupted I/O operation.", HttpStatus.CONFLICT);
		} else {
			return new ResponseEntity<>("No presentationdrafts availiable", HttpStatus.NOT_FOUND);
		}
	}

	@ApiOperation(value = " ", hidden = true)
	@GetMapping("api/pdf/{id}")
	public ResponseEntity<Object> getPresentationDraft(
			@ApiParam(required = true, name = "id", value = "PresentationDraft ID") @PathVariable("id") Long id) {
		int result = pdfService.getPresentationDraftToPDF(id);
		if (result == 1) {
			return ResponseEntity.ok().build();
		} else if (result == 2) {
			return new ResponseEntity<>("Interrupted I/O operation.", HttpStatus.CONFLICT);
		} else {
			return new ResponseEntity<>("Could not find presentationdraft with the given ID", HttpStatus.NOT_FOUND);
		}
	}
}
