package com.topicus.CFPApplication.persistence;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.swing.JFileChooser;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.topicus.CFPApplication.domain.Conference;
import com.topicus.CFPApplication.domain.PresentationDraft;

@Service
@Transactional
public class ExcelService {

	private ConferenceService conferenceService;

	@Autowired
	public ExcelService(ConferenceService conferenceService) {
		this.conferenceService = conferenceService;
	}

	public void createExcel(Long id) {
		Optional<Conference> conference = conferenceService.findById(id);
		Set<PresentationDraft> tempList = conference.get().getPresentationDrafts();
		List<PresentationDraft> listPresentations = new ArrayList<>(tempList);

		String FILE_NAME = createAndSaveExcel();

		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("All PresentationDrafts");

		CellStyle leftAligned = workbook.createCellStyle();
		leftAligned.setAlignment(CellStyle.ALIGN_LEFT);

		initiateExcelHeader(sheet);

		int rowNum = 1;
		int colNum = 0;
		for (int i = 0; i < listPresentations.size(); i++) {
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
			cell.setCellValue(listPresentations.get(i).getSummary());

			cell = row.createCell(colNum++);
			cell.setCellValue(listPresentations.get(i).getTimeOfCreation().toString());

			cell = row.createCell(colNum++);
			cell.setCellValue(listPresentations.get(i).getType());

			colNum = 0;
		}

		for (int i = 0; i < sheet.getLastRowNum(); i++) {
			sheet.autoSizeColumn(i);
		}

		try {
			FileOutputStream outputStream = new FileOutputStream(FILE_NAME);
			workbook.write(outputStream);
			workbook.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("Done saving in: " + FILE_NAME);
	}

	public void initiateExcelHeader(XSSFSheet sheet) {
		String[] initialHeader = { "id", "Category", "Duration", "Label", "Subject", "Summary", "Time of Creation",
				"Type" };
		int colNum = 0;
		Row row = sheet.createRow(0);
		for (String str : initialHeader) {
			Cell cell = row.createCell(colNum++);
			cell.setCellValue(str);
		}
	}

	public String createAndSaveExcel() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setSelectedFile(new File("PresentationDrafts.xlsx"));
		fileChooser.setDialogTitle("Specify the location");
		int userSelection = fileChooser.showSaveDialog(null);

		if (userSelection == JFileChooser.APPROVE_OPTION) {
			File fileToSave = fileChooser.getSelectedFile();
			return fileToSave.getAbsolutePath();
		}
		return null;
	}
}
