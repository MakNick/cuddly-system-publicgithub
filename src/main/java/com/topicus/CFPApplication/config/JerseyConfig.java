package com.topicus.CFPApplication.config;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

import com.topicus.CFPApplication.api.ConferenceEndpoint;
import com.topicus.CFPApplication.api.ApplicantEndpoint;
import com.topicus.CFPApplication.api.PresentationDraftEndpoint;

@Component
@ApplicationPath("/api") // veranderen?
public class JerseyConfig extends ResourceConfig {
	public JerseyConfig(){
		register(PresentationDraftEndpoint.class);
		register(ConferenceEndpoint.class);
		register(ApplicantEndpoint.class);
		
	}
}


