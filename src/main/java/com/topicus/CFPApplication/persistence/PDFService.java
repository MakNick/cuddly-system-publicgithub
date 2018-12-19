package com.topicus.CFPApplication.persistence;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.topicus.CFPApplication.domain.Applicant;
import com.topicus.CFPApplication.domain.PDFWriter;
import com.topicus.CFPApplication.domain.PresentationDraft;
import com.topicus.CFPApplication.domain.PresentationDraftApplicant;

@Service
@Transactional
public class PDFService {

	@Autowired
	private PDFWriter pdfWriter;

	@Autowired
	private PresentationDraftRepository presentationDraftRepository;

	public Iterable<PresentationDraftApplicant> getPresentationDraftsToPDF() {
		Iterable<PresentationDraft> listPresentations = presentationDraftRepository.findAll();
		List<String> content = new ArrayList<>();

		for (PresentationDraft presentationDraft : listPresentations) {
			content.add("ID PresentationDraft: " + presentationDraft.getId());
			content.add("Subject: " + presentationDraft.getSubject());
			content.add("Category: " + presentationDraft.getCategory());
			content.add("Summary: "
					+ (presentationDraft.getSummary().contains("\n") ? null : presentationDraft.getSummary()));
			content.add("Type: " + presentationDraft.getType());
			content.add("Duration: " + presentationDraft.getDuration());
			content.add("Time of Creation: " + presentationDraft.getTimeOfCreation());

			for (Applicant applicant : presentationDraft.getApplicants()) {
				content.add("-------------------"); // seperating lines
				content.add("ID Applicant: " + applicant.getId());
				content.add("Name: " + applicant.getName());
				content.add("E-mail: " + applicant.getEmail());
				content.add("Phone Number: " + applicant.getPhonenumber());
				content.add("Occupation: " + applicant.getOccupation());
				content.add("Gender: " + applicant.getGender());
				content.add("Date of Birth: " + applicant.getDateOfBirth());
				content.add("Requests: " + applicant.getRequests());
			}
			content.add(" "); // to create new page
		}
		try {
			pdfWriter.savePdf(content);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void getPresentationDraftToPDF(Long id) {
		Optional<PresentationDraft> presentationDraft = presentationDraftRepository.findById(id);
		List<String> content = new ArrayList<>();
		Set<Applicant> applicantList = presentationDraft.get().getApplicants();
		content.add("ID PresentationDraft: " + presentationDraft.get().getId());
		content.add("Subject: " + presentationDraft.get().getSubject());
		content.add("Category: " + presentationDraft.get().getCategory());
		content.add("Summary: "
				+ (presentationDraft.get().getSummary().contains("\n") ? null : presentationDraft.get().getSummary()));
		content.add("Type: " + presentationDraft.get().getType());
		content.add("Duration: " + presentationDraft.get().getDuration());
		content.add("Time of Creation: " + presentationDraft.get().getTimeOfCreation());

		for (Applicant app : applicantList) {
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
		try {
			pdfWriter.savePdf(content, id);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}