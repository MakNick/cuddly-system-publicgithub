package com.topicus.CFPApplication.persistence;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.topicus.CFPApplication.domain.Applicant;

@RunWith(MockitoJUnitRunner.class)
public class ApplicantServiceTest {
	
	@Mock
	ApplicantRepository applicantRepository;
	
	@InjectMocks
	ApplicantService applicantService;
	
	@Test
	public void findAllTest(){
		
		List<Applicant> testList = new ArrayList<>();
		testList.add(new Applicant());
		
		Mockito.when(this.applicantRepository.findAll()).thenReturn(testList);
		
		List<Applicant> resultList = (List<Applicant>)this.applicantService.findAll();
		
		Mockito.verify(this.applicantRepository).findAll();
		
		Assert.assertEquals(1, resultList.size());
		
	}
	
	
	

}
