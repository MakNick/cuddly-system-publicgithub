package com.topicus.CFPApplication.persistence;

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

import com.topicus.CFPApplication.domain.Conference;
import com.topicus.CFPApplication.domain.PresentationDraft;

@RunWith(MockitoJUnitRunner.class)
public class PdfServiceTest {

	@Mock
	private PresentationDraftRepository presentationDraftRepository;

	@Mock
	private PdfWriter pdfWriter;

	@InjectMocks
	private PdfServiceNot pdfService;

	@Mock
	private ConferenceService conferenceService;

	@Test
	public void getAllPresentationDraftsToPDFTest() throws IOException {
		Conference conf = new Conference();
		conf.setId(1);

		PDDocument pdd = new PDDocument();

		Mockito.when(this.conferenceService.findById(conf.getId())).thenReturn(Optional.of(conf));
		Mockito.when(this.pdfService.getAllPresentationDraftsToPDF(conf.getId())).thenReturn(pdd);

		int result = 0;
		try {
			PDDocument document = pdfService.getAllPresentationDraftsToPDF(conf.getId());
			if (document == null) {
				result = 4;
			} else {
				result = 1;
			}
		} catch (NoSuchElementException e) {
			result = 2;
		} catch (IOException e) {
			result = 3;
		}

		Assert.assertEquals(1, result);
	}

	@Test
	public void getSinglePresentationDraftToPDFTest() {
		Conference conf = new Conference();
		conf.setId(1);

		PresentationDraft pd = new PresentationDraft();
		pd.setId(1);
		pd.setSubject("test");
		pd.setCategory("testCategory");
		pd.setSummary("testsummary");
		
		conf.addPresentationDraft(pd);
		
		Mockito.when(this.conferenceService.findById(conf.getId())).thenReturn(Optional.of(conf));
		int result = 0;
		try {
			PDDocument document = this.pdfService.getSinglePresentationDraftToPDF(pd.getId(), conf.getId());
			if (document == null) {
				result = 1;
			}
		} catch (NoSuchElementException e) {
			result = 2;
		} catch (FileNotFoundException fnfe) {
			result = 3;
		} catch (IOException e) {
			result = 4;
		}

		Assert.assertEquals(1, result);
	}

//	@Test
//	public void printAllPdfTest() throws PrinterException {
//		List<PresentationDraft> listpd = new ArrayList<>();
//		Mockito.when(this.presentationDraftRepository.findAll()).thenReturn(listpd);
//		int result = pdfService.printAllPdf();
//		Mockito.verify(this.presentationDraftRepository).findAll();
//		Assert.assertEquals(3, result);
//	}
//	
//	@Test
//	public void printSinglePdf() throws PrinterException {
//		Optional<PresentationDraft> pd = Optional.of(new PresentationDraft());
//		pd.get().setId(0);
//		pd.get().setId(1);
//		Mockito.when(this.presentationDraftRepository.findById((long) 1)).thenReturn(pd);
//		int result = this.pdfService.printSinglePdf((long) 1);
//		Mockito.verify(this.presentationDraftRepository).findById((long) 1);
//		Assert.assertEquals(1, result);
//	}
}
