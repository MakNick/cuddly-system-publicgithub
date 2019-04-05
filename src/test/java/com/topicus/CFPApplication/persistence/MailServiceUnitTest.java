//package com.topicus.CFPApplication.persistence;
//
//import static org.junit.Assert.assertEquals;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.invocation.InvocationOnMock;
//import org.mockito.junit.MockitoJUnitRunner;
//import org.mockito.stubbing.Answer;
//import org.springframework.mail.MailException;
//import org.springframework.mail.javamail.JavaMailSenderImpl;
//import org.springframework.mail.javamail.MimeMessagePreparator;
//
//import com.topicus.CFPApplication.domain.Applicant;
//import com.topicus.CFPApplication.domain.PresentationDraftRequest;
//import com.topicus.CFPApplication.persistence.mail.MailService;
//
//@RunWith(MockitoJUnitRunner.class)
//public class MailServiceUnitTest {
//	
//	@Mock
//	JavaMailSenderImpl emailSender;
//	
//	@InjectMocks
//	MailService mailService;
//	
////	@Test
////	public void sendSpecifiedEmailTest () {
////		PresentationDraftRequest p = new PresentationDraftRequest();
////    	p.setId(1);
////    	Applicant a = new Applicant();
////    	a.setEmail("jojo@gangster.com");
////    	Applicant b = new Applicant();
////    	b.setEmail("gromer@gaffer.nl");
////    	p.addApplicant(a);
////    	p.addApplicant(b);
////    	String text = "Dit moet in de mail terecht komen";
////	    	
////    	Mockito.doAnswer(new Answer() {
////			public Object answer(InvocationOnMock invocation) {
////			return null;
////			}
////		}).when(emailSender.send(Mockito.any(MimeMessagePreparator.class)));
////
////	    }
//
//}
//
