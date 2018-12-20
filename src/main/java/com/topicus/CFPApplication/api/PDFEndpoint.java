package com.topicus.CFPApplication.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.topicus.CFPApplication.persistence.PDFService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@Api(value = "PDFEndpoint", description = "create PDF")
public class PDFEndpoint {

	private PDFService pdfService;

	@Autowired
	public PDFEndpoint(PDFService pdfService) {
		this.pdfService = pdfService;
	}

	@ApiOperation("Get all presentationDrafts and create PDF")
	@GetMapping("api/pdf")
	public void createPDF() {
		pdfService.getPresentationDraftsToPDF();
	}

	@GetMapping("api/pdf/{id}")
	public void getPresentationDraft(
			@ApiParam(required = true, name = "hallo", value = "PresentationDraft ID") @PathVariable("id") Long id) {
		pdfService.getPresentationDraftToPDF(id);

	}
}
