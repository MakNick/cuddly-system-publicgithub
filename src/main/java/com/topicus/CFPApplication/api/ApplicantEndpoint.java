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
import com.topicus.CFPApplication.persistence.ApplicantService;

@Path("applicant")
@Component
public class ApplicantEndpoint {

	@Autowired
	private ApplicantService applicantService;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response listApplicants() {
		Iterable<Applicant> applicants = applicantService.findAll();
		return Response.ok(applicants).build();
	}

	/*
	 * LIJKT OVERBODIG DOORDAT EEN APPLICANT ALLEEN IN COMBINATIE MET EEN
	 * PRESENTATIONDRAFT WORDT GEPOST. DIE METHODE STAAT IN PRESENTATIONDRAFTEND
	 * 
	 * @POST
	 * 
	 * @Consumes(MediaType.APPLICATION_JSON)
	 * 
	 * @Produces(MediaType.APPLICATION_JSON) public Response save(Applicant
	 * applicant) { Applicant result = applicantService.save(applicant); return
	 * Response.accepted(result).build(); }
	 */

	@Path("{id}")
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response findById(@PathParam("id") Long id) {
		Optional<Applicant> result = this.applicantService.findById(id);
		if (result.isPresent()) {
			return Response.ok(result.get()).build();
		} else {
			return Response.status(404).build();
		}
	}
}
