package com.topicus.CFPApplication;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.topicus.CFPApplication.domain.Conference;
import com.topicus.CFPApplication.persistence.ConferenceRepository;

@SpringBootApplication
public class CfpApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(CfpApplication.class, args);
	}
}
