package com.topicus.CFPApplication.persistence;

import java.awt.print.PrinterException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.topicus.CFPApplication.domain.Applicant;
import com.topicus.CFPApplication.domain.Conference;
import com.topicus.CFPApplication.domain.PresentationDraft;

@Service
@Transactional
public class PdfService {
	private PdfWriter pdfWriter;
	private ConferenceService conferenceService;

	@Autowired
	public PdfService(PdfWriter pdfWriter, ConferenceService conferenceService) {
		this.pdfWriter = pdfWriter;
		this.conferenceService = conferenceService;
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
			PDDocument pdd = pdfWriter.saveAllPresentationDraft(content);
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
				PDDocument pdd = pdfWriter.saveSinglePresentationDrafts(content, id);
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
				pdfWriter.printAllPdf(content);
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
					pdfWriter.printSinglePdf(content, id);
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
			content.add("*Summary: " + presentationDraft.getSummary());
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
}