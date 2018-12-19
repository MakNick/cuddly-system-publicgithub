package com.topicus.CFPApplication.api;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.topicus.CFPApplication.persistence.PDFService;
import com.topicus.CFPApplication.persistence.PresentationDraftService;

@RestController
public class PDFEndpoint {

	private PDFService pdfService;

	private PresentationDraftService presentationDraftService;
	
	@Autowired
	public PDFEndpoint(PresentationDraftService presentationDraftService, PDFService pdfService) {
		this.presentationDraftService = presentationDraftService;
		this.pdfService = pdfService;
		
	}

	@GetMapping("api/pdf")
	public void createPDF() {
		pdfService.getPresentationDraftsToPDF();
	}
	
//	@Path("{id}")
//	@GET
//	@Consumes(MediaType.APPLICATION_JSON)
//	@Produces(MediaType.APPLICATION_JSON)
//	public Response findById(@PathParam ("id")Long id) {
//		Optional<PresentationDraft> result = this.pdfService.findById(id);
//		if (result.isPresent()) { 
//			return Response.ok(result.get()).build();
//		} else {
//			return Response.status(404).build();
//		}
//	}
}
