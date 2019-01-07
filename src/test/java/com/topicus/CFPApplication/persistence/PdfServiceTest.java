//package com.topicus.CFPApplication.persistence;
//
//import java.awt.print.PrinterException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//import org.junit.Assert;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.junit.MockitoJUnitRunner;
//
//import com.topicus.CFPApplication.domain.PresentationDraft;
//
//@RunWith(MockitoJUnitRunner.class)
//public class PdfServiceTest {
//
//	@Mock
//	private PresentationDraftRepository presentationDraftRepository;
//	
//	@Mock
//	private PdfWriter pdfWriter;
//
//	@InjectMocks
//	private PdfService pdfService;
//
//	@Test
//	public void getPresentationDraftsToPDF() {
//		List<PresentationDraft> listJaviel = new ArrayList<>();
//		Mockito.when(this.presentationDraftRepository.findAll()).thenReturn(listJaviel);
//		int result = pdfService.getPresentationDraftsToPDF();
//		Mockito.verify(this.presentationDraftRepository).findAll();
//		Assert.assertEquals(3, result);
//	}
//
//	@Test
//	public void getPresentationDraftToPDFTest() {
//		Optional<PresentationDraft> pd = Optional.of(new PresentationDraft());
//		pd.get().setId(1);
//		Mockito.when(this.presentationDraftRepository.findById((long) 1)).thenReturn(pd);
//		int result = this.pdfService.getPresentationDraftToPDF((long) 1);
//		Mockito.verify(this.presentationDraftRepository).findById((long) 1);
//		Assert.assertEquals(1, result);
//	}
//
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
//}