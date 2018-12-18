package com.topicus.CFPApplication.api;

import java.util.Optional;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.topicus.CFPApplication.domain.Applicant;
import com.topicus.CFPApplication.domain.PresentationDraft;
import com.topicus.CFPApplication.persistence.PDFService;
import com.topicus.CFPApplication.persistence.PresentationDraftService;

@Path("pdf")
@Component
public class PDFEndpoint {

	@Autowired
	private PDFService pdfService;

	@Autowired
	private PresentationDraftService presentationDraftService;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public void createPDF() {
		pdfService.getApplicantsToPDF();
	}



}
