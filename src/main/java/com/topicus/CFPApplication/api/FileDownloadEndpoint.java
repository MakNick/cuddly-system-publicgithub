package com.topicus.CFPApplication.api;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.NoSuchElementException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.topicus.CFPApplication.persistence.ExcelService;
import com.topicus.CFPApplication.persistence.PdfService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@Api(value = "FileDownloadEndpoint", description = "Create Excel and PDF files")
public class FileDownloadEndpoint {

	private ExcelService excelService;
	private PdfService pdfService;

	@Autowired
	public FileDownloadEndpoint(ExcelService excelService, PdfService pdfService) {
		this.excelService = excelService;
		this.pdfService = pdfService;
	}

	@ApiOperation("Creates Excel spreadsheet from specified conference ID")
	@ApiResponses({ @ApiResponse(code = 200, message = "Successfully created Excel spreadsheet"),
			@ApiResponse(code = 404, message = "Conference and/or conference ID not found"),
			@ApiResponse(code = 409, message = "I/O Exception"), })
	@GetMapping("api/{conferenceId}/download/excel")
	public ResponseEntity<byte[]> createExcel(
			@ApiParam(required = true, name = "conferenceId", value = "Conference ID") @PathVariable("conferenceId") Long conferenceId) {
		if (conferenceId > 0 && conferenceId != null) {
			try {
				XSSFWorkbook wb = excelService.createExcel(conferenceId);
				if (wb != null) {
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					wb.write(baos);
					byte[] outputArray = baos.toByteArray();
					HttpHeaders headers = new HttpHeaders();
					headers.add("Content-Type", "application/octet-stream");
					headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
					headers.setContentLength(outputArray.length);
					ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(outputArray, headers, HttpStatus.OK);
					wb.close();
					baos.close();
					return response;
				} else {
					return new ResponseEntity<>(new byte[0], HttpStatus.NOT_FOUND);
				}
			} catch (NoSuchElementException nsee) {
				return new ResponseEntity<>(new byte[0], HttpStatus.NOT_FOUND);
			} catch (IOException e) {
				return new ResponseEntity<>(new byte[0], HttpStatus.CONFLICT);
			}
		}
		return new ResponseEntity<>(new byte[0], HttpStatus.NOT_FOUND);
	}

	@ApiOperation(value = "Get all presentationDrafts and create PDF")
	@ApiResponses({ @ApiResponse(code = 200, message = "Create PDF succesfully"),
			@ApiResponse(code = 404, message = "No presentationdraft and/or conference available"),
			@ApiResponse(code = 412, message = "Cancelled save request") })
	@GetMapping("api/{conferenceId}/download/pdf")
	public ResponseEntity<byte[]> getAllPdf(
			@ApiParam(required = true, name = "conferenceId", value = "Conference ID") @PathVariable("conferenceId") Long conferenceId)
			throws IOException {
		if (conferenceId > 0 && conferenceId != null) {
			PDDocument result = pdfService.getAllPresentationDraftsToPDF(conferenceId);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			if (result != null) {
				try {
					result.save(baos);
					byte[] outputArray = baos.toByteArray();
					HttpHeaders headers = new HttpHeaders();
					headers.add("Content-Type", "application/octet-stream");
					headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
					headers.setContentLength(outputArray.length);
					ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(outputArray, headers, HttpStatus.OK);
					baos.close();
					return response;
				} catch (IOException e) {
					return new ResponseEntity<>(new byte[0], HttpStatus.CONFLICT);
				}
			} else {
				return new ResponseEntity<>(new byte[0], HttpStatus.NOT_FOUND);
			}
		}
		return new ResponseEntity<>(new byte[0], HttpStatus.NOT_FOUND);
	}

	@ApiOperation(value = "Get single presentationDraft and create PDF ")
	@ApiResponses({ @ApiResponse(code = 200, message = "Create PDF succesfully"),
			@ApiResponse(code = 400, message = "Invalid conference ID and/or presentationdraft ID value"),
			@ApiResponse(code = 404, message = "No presentationdraft ID available"),
			@ApiResponse(code = 412, message = "Cancelled save request") })
	@GetMapping("api/{conferenceId}/download/pdf/{id}")
	public ResponseEntity<byte[]> getSinglePdf(
			@ApiParam(required = true, name = "id", value = "PresentationDraft ID") @PathVariable("id") Long id,
			@ApiParam(required = true, name = "conferenceId", value = "Conference ID") @PathVariable("conferenceId") Long conferenceId)
			throws IOException {
		if (conferenceId > 0 && conferenceId != null) {
			if (id != null && id != 0) {
				PDDocument result = pdfService.getSinglePresentationDraftToPDF(id, conferenceId);
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				if (result != null) {
					try {
						result.save(baos);
						byte[] outputArray = baos.toByteArray();
						HttpHeaders headers = new HttpHeaders();
						headers.add("Content-Type", "application/octet-stream");
						headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
						headers.setContentLength(outputArray.length);
						ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(outputArray, headers,
								HttpStatus.OK);
						baos.close();
						return response;
					} catch (IOException e) {
						return new ResponseEntity<>(new byte[0], HttpStatus.CONFLICT);
					}
				} else {
					return new ResponseEntity<>(new byte[0], HttpStatus.NOT_FOUND);
				}
			} else {
				return new ResponseEntity<>(new byte[0], HttpStatus.NOT_FOUND);
			}
		}
		return new ResponseEntity<>(new byte[0], HttpStatus.NOT_FOUND);
	}
}
