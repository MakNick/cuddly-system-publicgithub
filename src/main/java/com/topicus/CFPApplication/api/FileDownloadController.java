package com.topicus.CFPApplication.api;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.topicus.CFPApplication.domain.PdfWriter;
import com.topicus.CFPApplication.persistence.PdfService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@Api(value = "File Download Endpoint", description = "create PDF", hidden = true)
public class FileDownloadController {

	private PdfService pdfService;

	@Autowired
	public FileDownloadController(PdfService pdfService, PdfWriter pdfWriter) {
		this.pdfService = pdfService;
	}

	// Using HttpServletResponse
	@GetMapping("api/download/pdf")
	public ResponseEntity<Object> getAllPresentationDraftPdfFile(HttpServletResponse response) throws IOException {
		String FILE_PATH = "C:\\Users\\JusreizaKadir\\Desktop\\download3.pdf";
		String result = pdfService.getPresentationDraftsToPDF();
		File file = new File(".");
		if (file.canRead()) {
			if (result == "1") {
				response.setContentType("application/pdf");
				response.setHeader("Content-Disposition", "attachment;filename=" + file.getName());
				BufferedInputStream inStrem = new BufferedInputStream(new FileInputStream(FILE_PATH));
				BufferedOutputStream outStream = new BufferedOutputStream(response.getOutputStream());
				byte[] buffer = new byte[1024];
				int bytesRead = 0;

				while ((bytesRead = inStrem.read(buffer)) != -1) {
					outStream.write(buffer, 0, bytesRead);
				}
				outStream.flush();
				inStrem.close();
				System.out.println("\nall PresentationDrafts are saved\n");
				return ResponseEntity.ok().build();
			} else if (FILE_PATH == "2") {
				return new ResponseEntity<>("Interrupted I/O operation.", HttpStatus.CONFLICT);
			} else {
				return new ResponseEntity<>("No presentationdrafts availiable", HttpStatus.NOT_FOUND);
			}
		}
		System.out.println("No file found");
		return new ResponseEntity<>("No file found", HttpStatus.BAD_REQUEST);
	}

	@ApiOperation(value = " ", hidden = true)
	@GetMapping("api/download/pdf/{id}")
	public ResponseEntity<Object> getSinglePresentationDraftPdfFile(HttpServletResponse response,
			@ApiParam(required = true, name = "id", value = "PresentationDraft ID") @PathVariable("id") Long id)
			throws IOException {
		String FILE_PATH = "C:\\Users\\JusreizaKadir\\Desktop\\download3.pdf";
		String result = pdfService.getPresentationDraftToPDF(id);
		File file = new File(".");

		if (file.canRead()) {
			if (result == "1") {

				response.setContentType("application/pdf");
				response.setHeader("Content-Disposition", "attachment;filename=" + file.getName());
				BufferedInputStream inStrem = new BufferedInputStream(new FileInputStream(FILE_PATH));
				BufferedOutputStream outStream = new BufferedOutputStream(response.getOutputStream());
				byte[] buffer = new byte[1024];
				int bytesRead = 0;

				while ((bytesRead = inStrem.read(buffer)) != -1) {
					outStream.write(buffer, 0, bytesRead);
				}
				outStream.flush();
				inStrem.close();
				System.out.println("\nPresentationDraft with ID: " + id + " is saved\n");
				return ResponseEntity.ok().build();

			} else if (result == "2") {
				return new ResponseEntity<>("Interrupted I/O operation.", HttpStatus.CONFLICT);
			} else {
				return new ResponseEntity<>("Could not find presentationdraft with the given ID", HttpStatus.NOT_FOUND);
			}
		}
		System.out.println("No file found");
		return new ResponseEntity<>("No file found", HttpStatus.BAD_REQUEST);
	}

}
