package com.topicus.CFPApplication.api;

import java.util.Optional;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.topicus.CFPApplication.domain.Conference;
import com.topicus.CFPApplication.persistence.ConferenceService;

@Path("conference")
@Component
public class ConferenceEndpoint {
	
	@Autowired
	private ConferenceService conferenceService;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response conferenceList() {
//	conferenceService.save(new Conference());
		Iterable<Conference> result = conferenceService.findAll();
		return Response.ok(result).build();
	}
	
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getConferenceById(@PathParam("id")Long id) {
		Optional<Conference> result = conferenceService.findById(id);
		if(result.isPresent()) {
			return Response.ok(result.get()).build();
		}
		return Response.status(404).build();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response saveConference(Conference conference){
		return Response.accepted(conferenceService.save(conference)).build();
	}

}
