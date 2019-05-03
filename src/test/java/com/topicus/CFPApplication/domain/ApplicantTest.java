package com.topicus.CFPApplication.domain;

import com.topicus.CFPApplication.constants.Gender;
import com.topicus.CFPApplication.persistence.ApplicantRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;

@RunWith(MockitoJUnitRunner.class)
public class ApplicantTest {

	@Mock
	private ApplicantRepository applicantRepository;

	@Test
	public void createApplicant() {

		Applicant applicant = new Applicant();

		final String email = "test@test.nl";
		final String name = "test";
		final LocalDate dateOfBirth = LocalDate.now();
		final String occupation = "testing stuff";
		final String phoneNumber = "1234567810";
		final String requests = "Give me a special hat";

		applicant.setEmail(email);
		applicant.setName(name);
		applicant.setDateOfBirth(dateOfBirth);
		applicant.setGender(Gender.MALE);
		applicant.setOccupation(occupation);
		applicant.setPhoneNumber(phoneNumber);
		applicant.setRequests(requests);
		applicant.setPresentations(new HashSet<>(Arrays.asList(new Presentation(), new Presentation())));
		applicant.setPresentationDrafts(new HashSet<>(Arrays.asList(new PresentationDraft(), new PresentationDraft())));

		Assert.assertEquals(2, applicant.getPresentationDrafts().size());
		Assert.assertEquals(2, applicant.getPresentations().size());
		Assert.assertEquals(email, applicant.getEmail());
		Assert.assertEquals(name, applicant.getName());
		Assert.assertEquals(dateOfBirth, applicant.getDateOfBirth());
		Assert.assertEquals(Gender.MALE, applicant.getGender());
		Assert.assertEquals(occupation, applicant.getOccupation());
		Assert.assertEquals(phoneNumber, applicant.getPhoneNumber());
		Assert.assertEquals(requests, applicant.getRequests());
		
	}

}
