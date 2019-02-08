package com.topicus.CFPApplication.persistence;

import java.awt.print.PrinterException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.topicus.CFPApplication.domain.Applicant;
import com.topicus.CFPApplication.domain.Conference;
import com.topicus.CFPApplication.domain.PresentationDraft;

@Service
@Transactional
public class PdfService {

	private ConferenceService conferenceService;
	private PrintService printService;

	@Autowired
	public PdfService(ConferenceService conferenceService, PrintService printService) {
		this.conferenceService = conferenceService;
		this.printService = printService;
	}

	public PDDocument getAllPresentationDraftsToPDF(Long conferenceId) throws IOException, NoSuchElementException {
		Optional<Conference> conferenceOpt = conferenceService.findById(conferenceId);
		List<String> content = new ArrayList<>();
		if (conferenceOpt.isPresent()) {
			List<PresentationDraft> listPresentations = new ArrayList<>(conferenceOpt.get().getPresentationDrafts());
			listPresentations = sortList(listPresentations);
			for (int i = 0; i < listPresentations.size(); i++) {
				addContent(content, listPresentations.get(i));
				if (i != listPresentations.size() - 1) {
					content.add(" "); // to create new page for a different presentationDraft
				}
			}
			PDDocument pdd = saveAllPresentationDraft(content);
			return pdd;
		}
		throw new NoSuchElementException();
	}

	public PDDocument getSinglePresentationDraftToPDF(long id, Long conferenceId)
			throws IOException, NoSuchElementException {
		Optional<Conference> conferenceOpt = conferenceService.findById(conferenceId);
		List<String> content = new ArrayList<>();
		if (conferenceOpt.isPresent()) {
			List<PresentationDraft> listPresentations = new ArrayList<>(conferenceOpt.get().getPresentationDrafts());
			listPresentations = sortList(listPresentations);
			for (int i = 0; i < listPresentations.size(); i++) {
				if (listPresentations.get(i).getId() == id) {
					addContent(content, listPresentations.get(i));
				}
			}
			if (content.isEmpty()) {
				throw new FileNotFoundException();
			} else {
				PDDocument pdd = saveSinglePresentationDrafts(content, id);
				return pdd;
			}
		}
		throw new NoSuchElementException();
	}

	public int printAllPdf(Long conferenceId) throws PrinterException {
		Optional<Conference> conferenceOpt = conferenceService.findById(conferenceId);
		List<String> content = new ArrayList<>();
		if (conferenceOpt.isPresent()) {
			List<PresentationDraft> listPresentations = new ArrayList<>(conferenceOpt.get().getPresentationDrafts());
			listPresentations = sortList(listPresentations);
			for (int i = 0; i < listPresentations.size(); i++) {
				addContent(content, listPresentations.get(i));
				if (i != listPresentations.size() - 1) {
					content.add(" "); // to create new page for a different presentationDraft
				}
			}
			try {
				printAllPdf(content);
				return 1;
			} catch (IOException e) {
				return 2;
			}
		}
		return 3;
	}

	public int printSinglePdf(long id, Long conferenceId) throws PrinterException {
		Optional<Conference> conferenceOpt = conferenceService.findById(conferenceId);

		List<String> content = new ArrayList<>();
		if (conferenceOpt.isPresent()) {
			List<PresentationDraft> listPresentations = new ArrayList<>(conferenceOpt.get().getPresentationDrafts());
			listPresentations = sortList(listPresentations);
			for (int i = 0; i < listPresentations.size(); i++) {
				if (listPresentations.get(i).getId() == id) {
					addContent(content, listPresentations.get(i));
				}
			}
			if (content.isEmpty()) {
				return -1;
			} else {
				try {
					printSinglePdf(content, id);
					return 1;
				} catch (IOException e) {
					return 2;
				}
			}
		}
		return 3;
	}

	private List<PresentationDraft> sortList(List<PresentationDraft> sortedPresentationDraft) {
		boolean again = false;
		for (int x = 0; x < sortedPresentationDraft.size() - 1; x++) {
			PresentationDraft first = sortedPresentationDraft.get(x);
			PresentationDraft second = sortedPresentationDraft.get(x + 1);
			if (first.getId() < second.getId()) {
			} else {
				sortedPresentationDraft.set(x, second);
				sortedPresentationDraft.set(x + 1, first);
				again = true;
			}
		}
		if (again) {
			sortList(sortedPresentationDraft);
		}
		return sortedPresentationDraft;
	}

	private void addContent(List<String> content, PresentationDraft presentationDraft) {
		content.add("ID PresentationDraft: " + presentationDraft.getId());
		content.add("Subject: "
				+ presentationDraft.getSubject().replaceAll("\n", "").replaceAll("\r", "").replaceAll("\t", ""));
		content.add("Category: "
				+ presentationDraft.getCategory().replaceAll("\n", "").replaceAll("\r", "").replaceAll("\t", ""));
		if (presentationDraft.getSummary().contains("\n") || presentationDraft.getSummary().contains("\r")
				|| presentationDraft.getSummary().contains("\t")) {
			content.add("Summary: "
					+ presentationDraft.getSummary().replaceAll("\n", "").replaceAll("\r", "").replaceAll("\t", ""));
		} else {
			content.add("Summary: " + presentationDraft.getSummary());
		}
		content.add("Type: " + presentationDraft.getType());
		content.add("Duration: " + presentationDraft.getDuration());
		content.add("Time of Creation: " + presentationDraft.getTimeOfCreation());
		for (Applicant app : presentationDraft.getApplicants()) {
			content.add("---------------------------------------*"); // seperating lines
			content.add("ID Applicant: " + app.getId());
			content.add("Name: " + app.getName().replaceAll("\n", "").replaceAll("\r", "").replaceAll("\t", ""));
			content.add("E-mail: " + app.getEmail().replaceAll("\n", "").replaceAll("\r", "").replaceAll("\t", ""));
			content.add("Phone Number: " + app.getPhonenumber());
			content.add("Occupation: " + app.getOccupation());
			content.add("Gender: " + app.getGender());
			content.add(
					"Requests: " + app.getRequests().replaceAll("\n", "").replaceAll("\r", "").replaceAll("\t", ""));
		}
	}
	public PDDocument createPdfFile(List<String> content, Long id) throws IOException {
		PDDocument pdDocument = new PDDocument(); // Creating PDF document object
		PDPage page = new PDPage(); // Creating a blank page
		PDPageContentStream contentStream = new PDPageContentStream(pdDocument, page);
		pdDocument.addPage(page);// Adding the blank page to the document
		PDFont pdfFont = PDType1Font.HELVETICA; // Select font
		float fontSize = 10f; // font size
		float lineSpace = 1.3f * fontSize; // The amount of space from the bottom of one line of text to the bottom of
											// the next line
		PDRectangle mediabox = page.getMediaBox(); // Creating margins
		float margin = 50f;
		float width = mediabox.getWidth() - 1.7f * margin;
		float startX = mediabox.getLowerLeftX() + margin;
		float startY = mediabox.getUpperRightY() - margin;
		List<String> lines = new ArrayList<String>();
		for (String text : content) {
			int lastSpace = -1;
			while (text.length() > 0) { // if the text reaches the end of the page, it will continue on the next line
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
		contentStream.beginText();// Begin the content stream
		contentStream.setFont(pdfFont, fontSize);// Setting the font to the content stream
		contentStream.newLineAtOffset(startX, startY);// Setting the position for the line
		float currentY = startY;
		for (String line : lines) { // Adding text in the form of string
			currentY -= lineSpace;
			// if the currentY hits the margin, line will continue on the next page
			// if line is equal to " " (see PdfService.class), the text will continue on the
			// next page
			if (currentY <= margin || " ".equals(line)) {
				contentStream.endText();
				contentStream.close();
				PDPage newPage = new PDPage();
				pdDocument.addPage(newPage);
				contentStream = new PDPageContentStream(pdDocument, newPage);
				contentStream.beginText();
				contentStream.setFont(pdfFont, fontSize);
				contentStream.newLineAtOffset(startX, startY);
				currentY = startY;
			}
			contentStream.showText(line); // Add the text on the content stream
			contentStream.newLineAtOffset(0, -lineSpace);
		}
		contentStream.endText();// Ending the content stream
		contentStream.close();// Closing the content stream
		return pdDocument;
	}

	public PDDocument saveSinglePresentationDrafts(List<String> content, Long id) throws IOException {
		PDDocument document = createPdfFile(content, id);
		return document;
	}

	public PDDocument saveAllPresentationDraft(List<String> content) throws IOException {
		PDDocument document = createPdfFile(content, 0l);
		return document;
	}

	public void printSinglePdf(List<String> content, Long id) throws PrinterException, IOException {
		PDDocument document = createPdfFile(content, id);
		printService.printDocument(document); // print the document
		document.close(); // Closing the document
	}

	public void printAllPdf(List<String> content) throws PrinterException, IOException {
		PDDocument document = createPdfFile(content, 0l);
		printService.printDocument(document); // print the document
		document.close(); // Closing the document
	}
	
}
