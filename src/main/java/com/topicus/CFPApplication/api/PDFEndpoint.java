package com.topicus.CFPApplication.api;

import javax.ws.rs.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.topicus.CFPApplication.persistence.PDFService;

@RestController
public class PDFEndpoint {

	private PDFService pdfService;
	
	@Autowired
	public PDFEndpoint(PDFService pdfService) {
		this.pdfService = pdfService;
	}

	@GetMapping("api/pdf")
	public void createPDF() {
		pdfService.getPresentationDraftsToPDF();
	}

	@GetMapping("api/pdf/{id}")
	public void getPresentationDraft(@PathParam("id") Long id) {
		pdfService.getPresentationDraftToPDF(id);

	}
}
