package com.topicus.CFPApplication.persistence;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
	
	@Test
	public void saveTest() {
		Applicant appl = new Applicant();
		 
		Mockito.when(this.applicantRepository.save(appl)).thenReturn(appl);

		Applicant testItem = this.applicantRepository.save(appl);

		Mockito.verify(this.applicantRepository).save(appl);

		Assert.assertEquals(appl, testItem);
	}

	@Test
	public void findByIdTest() {
		Optional<Applicant> opt = Optional.of(new Applicant());
		opt.get().setId(1);

		Mockito.when(this.applicantRepository.findById((long) 1)).thenReturn(opt);

		Applicant testItem = this.applicantService.findById((long) 1).get();

		Mockito.verify(this.applicantRepository).findById((long) 1);

		Assert.assertEquals(1, testItem.getId());
	}
	
	@Test
	public void findExistingApplicantTest() {
		Optional<Applicant> opt = Optional.of(new Applicant());
		opt.get().setName("jojo");
		opt.get().setEmail("jojo@gangster.nl");
		
		Mockito.when(this.applicantRepository.findApplicantByNameAndEmail("jojo", "jojo@gangster.nl")).thenReturn(opt);
		
		Applicant testItem = this.applicantService.findExistingApplicant("jojo", "jojo@gangster.nl").get();
		
		Mockito.verify(this.applicantRepository).findApplicantByNameAndEmail("jojo", "jojo@gangster.nl");
		
		Assert.assertEquals("jojo", testItem.getName());
		Assert.assertEquals("jojo@gangster.nl", testItem.getEmail());
	}
}
