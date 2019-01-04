package com.topicus.CFPApplication.persistence;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.topicus.CFPApplication.domain.Applicant;
import com.topicus.CFPApplication.domain.Conference;
import com.topicus.CFPApplication.domain.PresentationDraft;

@Service
public class ExcelService {

	private ConferenceService conferenceService;
	private FileService fileService;

	@Autowired
	public ExcelService(ConferenceService conferenceService, FileService fileService) {
		this.conferenceService = conferenceService;
		this.fileService = fileService;
	}

	@SuppressWarnings("deprecation")
	public void createExcel(Long id) throws IOException, NoSuchElementException {
		Optional<Conference> conference = conferenceService.findById(id);

		if (conference.isPresent()) {
			List<PresentationDraft> listPresentations = new ArrayList<>(conference.get().getPresentationDrafts());
			XSSFWorkbook workbook = new XSSFWorkbook();
			XSSFSheet sheet = workbook.createSheet("All PresentationDrafts");
			String FILE_NAME = fileService.saveDocumentInSaveDialog("PresentationDrafts.xlsx");

			CellStyle leftAligned = workbook.createCellStyle();
			leftAligned.setAlignment(CellStyle.ALIGN_LEFT);
			CellStyle wrapText = workbook.createCellStyle();
			wrapText.setWrapText(true);

			initiateExcelHeader(sheet);

			int rowNum = 1;
			int colNum = 0;
			for (int i = 0; i < listPresentations.size(); i++) {
				List<Applicant> applicantList = new ArrayList<>(listPresentations.get(i).getApplicants());
				Row row = sheet.createRow(rowNum++);

				Cell cell = row.createCell(colNum++);
				cell.setCellStyle(leftAligned);
				cell.setCellValue(listPresentations.get(i).getId());

				cell = row.createCell(colNum++);
				cell.setCellValue(listPresentations.get(i).getCategory());

				cell = row.createCell(colNum++);
				cell.setCellStyle(leftAligned);
				cell.setCellValue(listPresentations.get(i).getDuration());

				cell = row.createCell(colNum++);
				cell.setCellValue(listPresentations.get(i).getLabel().toString());

				cell = row.createCell(colNum++);
				cell.setCellValue(listPresentations.get(i).getSubject());

				cell = row.createCell(colNum++);
				cell.setCellStyle(wrapText);
				cell.setCellValue(listPresentations.get(i).getSummary());

				cell = row.createCell(colNum++);
				cell.setCellValue(listPresentations.get(i).getTimeOfCreation().toString());

				cell = row.createCell(colNum++);
				cell.setCellValue(listPresentations.get(i).getType());

				for (int j = 0; j < applicantList.size(); j++) {
					sheet.setColumnHidden(colNum, true);
					cell = row.createCell(colNum++);
					cell.setCellValue(applicantList.get(j).getName());
					sheet.setColumnHidden(colNum, true);
					cell = row.createCell(colNum++);
					cell.setCellValue(applicantList.get(j).getEmail());
				}

				for (int q = 0; q < colNum; q++) {
					sheet.autoSizeColumn(q);
				}
				colNum = 0;
			}

			FileOutputStream outputStream = new FileOutputStream(FILE_NAME);
			workbook.write(outputStream);
			workbook.close();
		} else {
			throw new NoSuchElementException();
		}

	}

	public void initiateExcelHeader(XSSFSheet sheet) {
		String[] initialHeader = { "id", "Category", "Duration", "Label", "Subject", "Summary", "Time of Creation",
				"Type", "Applicant 1", "", "Applicant 2", "", "Applicant 3", "", "Applicant 4" };
		int colNum = 0;
		Row row = sheet.createRow(0);
		for (String str : initialHeader) {
			Cell cell = row.createCell(colNum++);
			cell.setCellValue(str);
		}
	}
}
