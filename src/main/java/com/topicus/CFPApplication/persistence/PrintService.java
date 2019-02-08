package com.topicus.CFPApplication.persistence;

import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.printing.PDFPageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

<<<<<<< HEAD

=======
>>>>>>> develop
@Service
@Transactional
public class PrintService {

<<<<<<< HEAD
//	public void printDocument(Document document) {
//		PDDocument doc = document.getPDDocument();
//		PrinterJob job = PrinterJob.getPrinterJob();
//		job.setPageable(new PDFPageable(doc));
//		try {
//			if (job.printDialog()) {
//				try {
//					job.print();
//				} catch (PrinterException e) {
//					e.printStackTrace();
//				}
//			}
//		} catch (HeadlessException he) {
//			he.printStackTrace();
//		}
//	}
=======
	public void printDocument(PDDocument document) {
		PrinterJob job = PrinterJob.getPrinterJob();
		job.setPageable(new PDFPageable(document));
		try {
			if (job.printDialog()) {
				try {
					job.print();
				} catch (PrinterException e) {
					e.printStackTrace();
				}
			}
		} catch (HeadlessException he) {
			he.printStackTrace();
		}
	}
>>>>>>> develop
}