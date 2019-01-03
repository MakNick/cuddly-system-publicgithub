package com.topicus.CFPApplication.domain;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PresentationTest {

	@Test
	public void addApplicantTest() {
		Presentation p = new Presentation();
		Applicant a = new Applicant();
		p.addApplicant(a);

		Assert.assertEquals(1, p.getApplicants().size());
	}

}
