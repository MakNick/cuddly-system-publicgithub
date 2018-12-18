package com.topicus.CFPApplication.persistence;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
	private ApplicantRepository applicantRepository;
	
	

	public Iterable<PresentationDraftApplicant> getApplicantsToPDF() {
		Iterable<Applicant> listApplicants = applicantRepository.findAll();
		List<String> content = new ArrayList<>();

		for (Applicant applicant : listApplicants) {

			content.add("ID: " + applicant.getId());
			content.add("Name: " + applicant.getName());
			content.add("E-mail: " + applicant.getEmail());
			content.add("Phone Number: " + applicant.getPhonenumber());
			content.add("Occupation: " + applicant.getOccupation());
			content.add("Gender: " + applicant.getGender());
			content.add("Date of Birth: " + applicant.getDateOfBirth());
			content.add("Requests: " + applicant.getRequests());

			for (PresentationDraft presentationDraft : applicant.getPresentationDrafts()) {

				content.add("ID: " + presentationDraft.getId());
				content.add("Subject: " + presentationDraft.getSubject());
				content.add("Category: " + presentationDraft.getCategory());
				content.add("Summary: " + presentationDraft.getSummary());
				content.add("Type: " + presentationDraft.getType());
				content.add("Duration: " + presentationDraft.getDuration());
				content.add("Time of Creation: " + presentationDraft.getTimeOfCreation());

			}
			content.add(" "); // to create new page
		}

		try {
			pdfWriter.writePdf(content);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}