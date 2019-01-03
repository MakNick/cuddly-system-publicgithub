package com.topicus.CFPApplication.persistence;

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
		List<PresentationDraft> listPresentations = (List<PresentationDraft>) presentationDraftRepository.findAll();
		List<String> content = new ArrayList<>();
		if (!listPresentations.isEmpty()) {
			for (int i = 0; i < listPresentations.size(); i++) {
				addContent(content, listPresentations.get(i));
				if (i != listPresentations.size() - 1) {
					content.add(" "); // to create new page
				}
			}
			try {
				pdfWriter.savePdf(content);
				return 1;
			} catch (IOException e) {
				return 2;
			}
		}
		return 3;
	}

	public int getPresentationDraftToPDF(Long id) {
		Optional<PresentationDraft> presentationDraft = presentationDraftRepository.findById(id);
		List<String> content = new ArrayList<>();
		if (presentationDraft.isPresent()) {
			content = addContent(content, presentationDraft.get());
			try {
				pdfWriter.savePdf(content, id);
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
}