package com.topicus.CFPApplication.persistence;

import java.awt.print.PrinterException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.topicus.CFPApplication.domain.Applicant;
import com.topicus.CFPApplication.domain.PresentationDraft;

@Service
@Transactional
public class PdfService {

	private PdfWriter pdfWriter;
	private PresentationDraftRepository presentationDraftRepository;

	@Autowired
	public PdfService(PdfWriter pdfWriter, PresentationDraftRepository presentationDraftRepository) {
		this.pdfWriter = pdfWriter;
		this.presentationDraftRepository = presentationDraftRepository;
	}

	public int getPresentationDraftsToPDF() {
		Iterable<PresentationDraft> iterableList = presentationDraftRepository.findAll();
		List<String> content = new ArrayList<>();
		if (iterableList instanceof List) {
			List<PresentationDraft> listPresentations = (List<PresentationDraft>) iterableList;
			if (!listPresentations.isEmpty()) {
				for (int i = 0; i < listPresentations.size(); i++) {
					addContent(content, listPresentations.get(i));
					if (i != listPresentations.size() - 1) {
						content.add(" "); // to create new page for a different presentationDraft
					}
				}
				try {
					pdfWriter.saveAllPresentationDraft(content);
					return 1;
				} catch (IOException e) {
					return 2;
				}

			}
			return 3;
		}
		return -1;
	}

	public int getPresentationDraftToPDF(Long id) {
		Optional<PresentationDraft> presentationDraft = presentationDraftRepository.findById(id);
		List<String> content = new ArrayList<>();
		if (presentationDraft.isPresent()) {
			content = addContent(content, presentationDraft.get());
			try {
				pdfWriter.saveSinglePresentationDrafts(content, id);
				return 1;
			} catch (IOException e) {
				return 2;
			}
		}
		return 3;
	}

	private List<String> addContent(List<String> content, PresentationDraft presentationDraft) {
		content.add("ID PresentationDraft: " + presentationDraft.getId());
		content.add("Subject: " + presentationDraft.getSubject());
		content.add("Category: " + presentationDraft.getCategory());
		System.out.println(">>"+presentationDraft.getSummary());
		if (presentationDraft.getSummary() != null) {
			content.add("Summary: "
					+ (presentationDraft.getSummary().contains("\n") ? null : presentationDraft.getSummary()));
		} else {
			content.add("Summary: ");
			
		}
		content.add("Type: " + presentationDraft.getType());
		content.add("Duration: " + presentationDraft.getDuration());
		content.add("Time of Creation: " + presentationDraft.getTimeOfCreation());

		for (Applicant app : presentationDraft.getApplicants()) {
			content.add("-------------------"); // seperating lines
			content.add("ID Applicant: " + app.getId());
			content.add("Name: " + app.getName());
			content.add("E-mail: " + app.getEmail());
			content.add("Phone Number: " + app.getPhonenumber());
			content.add("Occupation: " + app.getOccupation());
			content.add("Gender: " + app.getGender());
			content.add("Date of Birth: " + app.getDateOfBirth());
			content.add("Requests: " + app.getRequests());
		}
		return content;
	}

	public int printAllPdf() throws PrinterException {
		Iterable<PresentationDraft> iterableList = presentationDraftRepository.findAll();
		List<String> content = new ArrayList<>();
		if (iterableList instanceof List) {
			List<PresentationDraft> listPresentations = (List<PresentationDraft>) iterableList;
			if (!listPresentations.isEmpty()) {
				for (int i = 0; i < listPresentations.size(); i++) {
					addContent(content, listPresentations.get(i));
					if (i != listPresentations.size() - 1) {
						content.add(" "); // to create new page for a different presentationDraft
					}
				}
				try {
					pdfWriter.printAllPdf(content);
					return 1;
				} catch (IOException e) {
					return 2;
				}
			}
			return 3;
		}
		return -1;
	}

	public int printSinglePdf(Long id) throws PrinterException {
		Optional<PresentationDraft> presentationDraft = presentationDraftRepository.findById(id);
		List<String> content = new ArrayList<>();
		if (presentationDraft.isPresent()) {
			content = addContent(content, presentationDraft.get());
			try {
				pdfWriter.printSinglePdf(content, id);
				return 1;
			} catch (IOException e) {
				return 2;
			}
		}
		return 3;
	}
}