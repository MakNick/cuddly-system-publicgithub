package com.topicus.CFPApplication.persistence.services;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.topicus.CFPApplication.domain.PresentationDraft;
import com.topicus.CFPApplication.domain.conference.Conference;

//@RunWith(MockitoJUnitRunner.class)
public class PdfServiceTest {

	@Mock
	private PresentationDraftRepository presentationDraftRepository;

	@InjectMocks
	private PdfService pdfService;

	@Mock
	private ConferenceService conferenceService;

//	@Test
//	public void getSinglePresentationDraftToPDFTest() {
//		Conference conf = new Conference();
//		conf.setId(1);
//
//		PresentationDraftRequest pd = new PresentationDraftRequest();
//		pd.setId(1);
//		pd.setSubject("test");
//		pd.setCategory("testCategory");
//		pd.setSummary("testsummary");
//
//		conf.addPresentationDraft(pd);
//
//		Mockito.when(this.conferenceService.findById(conf.getId())).thenReturn(Optional.of(conf));
//		int result = 0;
//		try {
//			PDDocument document = this.pdfService.getSinglePresentationDraftToPDF(pd.getId(), conf.getId());
//			if (document == null) {
//				result = 1;
//			}
//		} catch (NoSuchElementException e) {
//			result = 2;
//		} catch (FileNotFoundException fnfe) {
//			result = 3;
//		} catch (IOException e) {
//			result = 4;
//		}
//
//		Assert.assertEquals(1, result);
//	}

}
