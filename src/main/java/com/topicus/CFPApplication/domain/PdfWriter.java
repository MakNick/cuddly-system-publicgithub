package com.topicus.CFPApplication.domain;

import java.awt.print.Pageable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.stereotype.Component;

@Component
public class PdfWriter {

	PdfWriter pdfWriter;

	PDDocument document;
	PrinterJob pj;

	public void writePdf(List<String> content, Long id) throws IOException {

		document = new PDDocument(); // Creating PDF document object
		PDPage page = new PDPage(); // Creating a blank page
		PDPageContentStream contentStream = new PDPageContentStream(document, page);

		document.addPage(page);// Adding the blank page to the document

		PDFont pdfFont = PDType1Font.COURIER; // Select font
		float fontSize = 10f;
		float leading = 1.5f * fontSize;

		PDRectangle mediabox = page.getMediaBox(); // Creating margins
		float margin = 52;
		float width = mediabox.getWidth() - 1.5f * margin;
		float startX = mediabox.getLowerLeftX() + margin;
		float startY = mediabox.getUpperRightY() - margin;

		List<String> lines = new ArrayList<String>();
		for (String text : content) {
			int lastSpace = -1;
			while (text.length() > 0) {
				int spaceIndex = text.indexOf(' ', lastSpace + 1);
				if (spaceIndex < 0)
					spaceIndex = text.length();
				String subString = text.substring(0, spaceIndex);
				float size = fontSize * pdfFont.getStringWidth(subString) / 1000;
				if (size > width) {
					if (lastSpace < 0)
						lastSpace = spaceIndex;
					subString = text.substring(0, lastSpace);
					lines.add(subString);
					text = text.substring(lastSpace).trim();
					lastSpace = -1;
				} else if (spaceIndex == text.length()) {
					lines.add(text);
					text = "";
				} else {
					lastSpace = spaceIndex;
				}
			}
		}
		contentStream.beginText();// Begin the Content stream
		contentStream.setFont(pdfFont, fontSize);// Setting the font to the Content stream
		contentStream.newLineAtOffset(startX, startY);// Setting the position for the line

		for (String line : lines) { // Adding text in the form of string
			if (" ".equals(line)) {
				contentStream.endText();
				contentStream.close();
				PDPage new_Page = new PDPage();
				document.addPage(new_Page);
				contentStream = new PDPageContentStream(document, new_Page);
				contentStream.beginText();
				contentStream.setFont(pdfFont, fontSize);
				contentStream.newLineAtOffset(startX, startY);
			}
			contentStream.showText(line);
			contentStream.newLineAtOffset(0, -leading);
		}
		contentStream.endText();// Ending the content stream
		contentStream.close();// Closing the content stream
	}

	public void savePdf(List<String> content, Long id) throws IOException {
		document = new PDDocument(); // Creating PDF document object
		writePdf(content, id);
		document.save(new File("presentationDraft" + id + ".pdf"));// Saving the document
		document.close(); // Closing the document
	}

	public void savePdf(List<String> content) throws IOException {
		document = new PDDocument(); // Creating PDF document object
		writePdf(content, 0L);
		document.save(new File("presentationDraftAll.pdf"));// Saving the document
		document.close(); // Closing the document
	}

	public void printAllPdf(List<String> content) throws PrinterException, IOException {
		document = new PDDocument();
		pj = PrinterJob.getPrinterJob();
		writePdf(content, 0L);
		pj.setPageable((Pageable) content);
		if (pj.printDialog()) {
			try {
				pj.print();
			} catch (PrinterException e) {
				System.out.println(e);
			}
		}
	}

	public void printSinglePdf(List<String> content, Long id) throws PrinterException, IOException {
		document = new PDDocument();
		pj = PrinterJob.getPrinterJob();
		writePdf(content, id);
		pj.setPageable((Pageable) content);
		if (pj.printDialog()) {
			try {
				pj.print();
			} catch (PrinterException e) {
				System.out.println(e);
			}
		}
	}
}