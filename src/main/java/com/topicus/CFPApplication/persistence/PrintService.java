package com.topicus.CFPApplication.persistence;

import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.printing.PDFPageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class PrintService {

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
}