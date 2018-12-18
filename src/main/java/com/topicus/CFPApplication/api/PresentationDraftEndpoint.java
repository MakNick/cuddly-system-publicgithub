package com.topicus.CFPApplication.api;

import java.util.Optional;
import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.topicus.CFPApplication.domain.Applicant;
import com.topicus.CFPApplication.domain.PresentationDraft;
import com.topicus.CFPApplication.domain.PresentationDraftApplicant;
import com.topicus.CFPApplication.persistence.PresentationDraftService;

@Path("presentationdraft")
@Component
public class PresentationDraftEndpoint {
	
	@Autowired
	private PresentationDraftService presentationDraftService;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response listPresentationDrafts() {
		//presentationDraftService.save(new PresentationDraft());
		Iterable<PresentationDraft> presentationDrafts = presentationDraftService.findAll();	
		return Response.ok(presentationDrafts).build();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response save(PresentationDraftApplicant presentationDraftApplicant) {	
		PresentationDraft presentationDraft = presentationDraftApplicant.getPresentationDraft();
		Set<Applicant> applicants = presentationDraftApplicant.getApplicants();
		presentationDraftService.linkPresentationDraftWithApplicants(presentationDraft, applicants);		
		return Response.accepted(presentationDraft).build();
	}
	
	@Path("{id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response findById(@PathParam ("id")Long id) {
		Optional<PresentationDraft> result = this.presentationDraftService.findById(id);
		if (result.isPresent()) {
			return Response.ok(result.get()).build();
		} else {
			return Response.status(404).build();
		}
	}
	
	@Path("/{id}/label/{value}")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public boolean changeLabel (@PathParam ("id")Long id, @PathParam("value")Integer value) {
		return this.presentationDraftService.changeLabel(id, value);
	}
	
/*	@Path("/delete/{id}")
	@DELETE
	@Consumes
	@Produces(MediaType.APPLICATION_JSON)
	public void delete (@PathParam ("id")Long id) {
		presentationDraftService.delete(id);
	}
*/	
	@Path("/delete/{id}")
	@DELETE
	@Consumes
	@Produces(MediaType.APPLICATION_JSON)
	public Response delete (@PathParam ("id")Long id) {
		presentationDraftService.delete(id);
		return Response.status(204).build();
	}
	
	@Path("/findbylabel/{label}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response listPresentationDraftsByLabel(@PathParam ("label")int value) {
		Iterable<PresentationDraft> presentationDraftsByLabel = presentationDraftService.findByLabel(value);	
		return Response.ok(presentationDraftsByLabel).build();
	}
	
	@Path("/finalize")
	@GET
	@Produces (MediaType.APPLICATION_JSON)
	public Response makePresentationDraftsFinal () {
		return presentationDraftService.makePresentationDraftsFinal();
	}
	
	@Path("/changepresentationdraft")
	@POST
	@Consumes (MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public PresentationDraft changePresentationDraft (PresentationDraft presentationDraft) {
		return presentationDraftService.save(presentationDraft);
	}
	
}
