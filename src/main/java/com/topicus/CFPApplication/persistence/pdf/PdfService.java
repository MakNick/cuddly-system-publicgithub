package com.topicus.CFPApplication.persistence.pdf;

import java.awt.print.PrinterException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.topicus.CFPApplication.domain.Applicant;
import com.topicus.CFPApplication.domain.Conference;
import com.topicus.CFPApplication.domain.PresentationDraft;
import com.topicus.CFPApplication.persistence.ConferenceService;
import com.topicus.CFPApplication.persistence.PrintService;

import rst.pdfbox.layout.elements.Document;
import rst.pdfbox.layout.elements.Element;
import rst.pdfbox.layout.elements.ImageElement;
import rst.pdfbox.layout.elements.Paragraph;
import rst.pdfbox.layout.elements.render.LayoutHint;
import rst.pdfbox.layout.elements.render.RenderContext;
import rst.pdfbox.layout.elements.render.RenderListener;
import rst.pdfbox.layout.elements.render.Renderer;
import rst.pdfbox.layout.elements.render.VerticalLayoutHint;
import rst.pdfbox.layout.text.Alignment;
import rst.pdfbox.layout.text.BaseFont;
import rst.pdfbox.layout.text.Position;
import rst.pdfbox.layout.text.TextFlow;
import rst.pdfbox.layout.text.TextFlowUtil;
import rst.pdfbox.layout.text.TextSequenceUtil;

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

	public Document getAllPresentationDraftsToPDF(Long conferenceId) throws IOException, NoSuchElementException {
		Optional<Conference> conferenceOpt = conferenceService.findById(conferenceId);
		List<String> content = new ArrayList<>();
		if (conferenceOpt.isPresent()) {
			List<PresentationDraft> listPresentations = new ArrayList<>(conferenceOpt.get().getPresentationDrafts());
			listPresentations = sortList(listPresentations);
			for (int i = 0; i < listPresentations.size(); i++) {
				addContent(content, listPresentations.get(i));
				if (i != listPresentations.size() - 1) {
					content.add("  "); // to create new page for a different presentationDraft
				}
			}
			Document document = saveAllPresentationDrafts(content);
			return document;
		}
		throw new NoSuchElementException();
	}

	public Document getSinglePresentationDraftToPDF(long id, Long conferenceId)
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
				Document document = saveSinglePresentationDraft(content, id);
				return document;
			}
		}
		throw new NoSuchElementException();
	}

	private Document createPdf(List<String> content, long id) throws IOException {
		Document document = new Document(40, 60, 40, 60);
		ImageElement image;
		if (new File("TopiconfHeaderLogo.png").exists()) {
			image = new ImageElement("TopiconfHeaderLogo.png");
		} else {
			image = new ImageElement(PdfService.class.getResourceAsStream("TopiconfHeaderLogo.png"));
		}
		image.setWidth(image.getWidth() / 1.5f);
		image.setHeight(image.getHeight() / 1.5f);
		document.add(image, new VerticalLayoutHint(Alignment.Right, 10, 10, 10, 10, true));

		SectionRenderer sectionRenderer = new SectionRenderer();
		document.addRenderer(sectionRenderer);
		document.addRenderListener(sectionRenderer);

		List<String> lines = new ArrayList<String>();
		for (String text : content) {
			lines.add(text);
		}
		for (String line : lines) {
			Paragraph paragraph = new Paragraph();
			paragraph.addMarkup(line, 10, BaseFont.Helvetica);
			document.add(paragraph);
			if ("  ".equals(line)) {
				document.add(new Section());
				document.add(image, new VerticalLayoutHint(Alignment.Right, 100, 10, 10, 10, true));
			}
		}
		return document;
	}

	public static class SectionRenderer implements Renderer, RenderListener {

		@Override
		public boolean render(RenderContext renderContext, Element element, LayoutHint layoutHint) throws IOException {
			if (element instanceof Section) {
				renderContext.newPage();
				return true;
			}
			return false;
		}

		public void beforePage(RenderContext renderContext) {
		}

		public void afterPage(RenderContext renderContext) throws IOException {
			String content = String.format("Pagina %s", renderContext.getPageIndex() + 1);
			TextFlow text = TextFlowUtil.createTextFlow(content, 8, PDType1Font.HELVETICA);
			float offset = renderContext.getPageFormat().getMarginLeft()
					+ TextSequenceUtil.getOffset(text, renderContext.getWidth(), Alignment.Right);
			text.drawText(renderContext.getContentStream(), new Position(offset, 30), Alignment.Right, null);
		}
	}

	public static class Section extends Paragraph {

		public Section() throws IOException {
			super();
		}
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
		content.add("__*PresentationDraft ID: *__" + "__*" + presentationDraft.getId() + "*__");
		content.add("\n*Subject: *"
				+ presentationDraft.getSubject().replaceAll("\\_", "\\\\\\_").replaceAll("\\*", "\\\\\\*"));
		content.add("*Category: *" + presentationDraft.getCategory());
		content.add("*Type: *" + presentationDraft.getType().replaceAll("\\_", "\\\\\\_").replaceAll("\\*", "\\\\\\*"));
		content.add("*Duration: *" + presentationDraft.getDuration());
		content.add("*Label: *" + presentationDraft.getLabel());
		content.add("*Time of Creation: *" + presentationDraft.getTimeOfCreation());
		content.add("\n*Summary: *\n" + presentationDraft.getSummary());
		for (Applicant app : presentationDraft.getApplicants()) {
			content.add("\n__Applicant ID: __" + "__" + app.getId() + "__");
			content.add("*Name: *" + app.getName().replaceAll("\n", ""));
			content.add("*E-mail: *" + app.getEmail().replaceAll("\n", "").replaceAll("\r", "").replaceAll("\t", "")
					.replaceAll("\\_", "\\\\\\_").replaceAll("\\*", "\\\\\\*"));
			content.add("*Phone Number: *" + app.getPhonenumber());
			content.add("*Occupation: *" + app.getOccupation());
			content.add("*Gender: *" + app.getGender());
			content.add("*Requests: *" + app.getRequests().replaceAll("\\_", "\\\\\\_").replaceAll("\\*", "\\\\\\*"));
		}
	}

	public Document saveAllPresentationDrafts(List<String> content) throws IOException {
		Document document = createPdf(content, 0l);
		return document;
	}

	public Document saveSinglePresentationDraft(List<String> content, Long id) throws IOException {
		Document document = createPdf(content, id);
		return document;
	}

	public Document printAllPdf(Long conferenceId) throws PrinterException, IOException {
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
			Document document = printAllPdf(content);
			return document;
		}
		throw new NoSuchElementException();
	}

	public Document printSinglePdf(long id, Long conferenceId) throws PrinterException, IOException {
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
				Document document = printSinglePdf(content, id);
				return document;
			}
		}
		throw new NoSuchElementException();
	}

	public Document printSinglePdf(List<String> content, Long id) throws PrinterException, IOException {
		Document document = createPdf(content, id);
		printService.printDocument(document); // print the document
		return document;
	}

	public Document printAllPdf(List<String> content) throws PrinterException, IOException {
		Document document = createPdf(content, 0l);
		printService.printDocument(document); // print the document
		return document;
	}
}