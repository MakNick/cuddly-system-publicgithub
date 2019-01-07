package com.topicus.CFPApplication.api;

import java.awt.print.PrinterException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.topicus.CFPApplication.persistence.PdfService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@Api(value = "PdfEndpoint", description = "Create PDF file")
public class PdfEndpoint {

	private PdfService pdfService;

	@Autowired
	public PdfEndpoint(PdfService pdfService) {
		this.pdfService = pdfService;
	}

	@ApiOperation(value = "Get all presentationDrafts and create PDF")
	@ApiResponses({ @ApiResponse(code = 200, message = "Create PDF succesfully"),
			@ApiResponse(code = 404, message = "No presentationdraft and/or conference available"),
			@ApiResponse(code = 412, message = "Cancelled save request") })
	@GetMapping("api/{conferenceId}/download/pdf")
	public ResponseEntity<> getAllPdf(
			@ApiParam(required = true, name = "conferenceId", value = "Conference ID") @PathVariable("conferenceId") Long conferenceId) {
		if (conferenceId > 0 && conferenceId != null) {
			int result = pdfService.getAllPresentationDraftsToPDF(conferenceId);
			if (result == 1) {
				return ResponseEntity.ok().build();
			} else if (result == 2) {
				return new ResponseEntity<>("Save request was cancelled", HttpStatus.CONFLICT);
			} else {
				return new ResponseEntity<>("No conference available", HttpStatus.NOT_FOUND);
			}
		}
		return new ResponseEntity<>("Invalid conference ID", HttpStatus.NOT_FOUND);
	}

//	@ApiOperation(value = "Get all presentationDrafts and create PDF")
//	@ApiResponses({ @ApiResponse(code = 200, message = "Create PDF succesfully"),
//			@ApiResponse(code = 404, message = "No presentationdraft and/or conference available"),
//			@ApiResponse(code = 412, message = "Cancelled save request") })
//	@GetMapping("api/{conferenceId}/download/pdf")
//	public ResponseEntity<byte[]> getAllPdf(
//			@ApiParam(required = true, name = "conferenceId", value = "Conference ID") @PathVariable("conferenceId") Long conferenceId) throws IOException {
//		if (conferenceId > 0 && conferenceId != null) {
//			PDDocument result = pdfService.getAllPresentationDraftsToPDF(conferenceId);
//			ByteArrayOutputStream baos = new ByteArrayOutputStream();
//			if (result != null) {
//				try {
//					result.save(baos);
//					byte[] outputArray = baos.toByteArray();
//					HttpHeaders headers = new HttpHeaders();
//					headers.add("Content-Type", "application/octet-stream");
//					headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
//					headers.setContentLength(outputArray.length);
//					ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(outputArray, headers, HttpStatus.OK);
//					baos.close();
//					result.close();
//					return response;
//				} catch (IOException e) {
//					return new ResponseEntity<>(new byte[0], HttpStatus.CONFLICT);
//				}
//			} else {
//				return new ResponseEntity<>(new byte[0], HttpStatus.NOT_FOUND);
//			}
//		}
//		return new ResponseEntity<>(new byte[0], HttpStatus.NOT_FOUND);
//	}

	@ApiOperation(value = "Get all presentationDrafts and print PDF")
	@ApiResponses({ @ApiResponse(code = 200, message = "Printing PDF succesfully"),
			@ApiResponse(code = 400, message = "A error occured while trying to print file"),
			@ApiResponse(code = 404, message = "No presentationdraft and/or conference available") })
	@GetMapping("api/{conferenceId}/print/pdf")
	public ResponseEntity<?> printAllPdf(
			@ApiParam(required = true, name = "conferenceId", value = "Conference ID") @PathVariable("conferenceId") Long conferenceId) {
		if (conferenceId > 0 && conferenceId != null) {
			try {
				int result = pdfService.printAllPdf(conferenceId);
				if (result == 1) {
					return ResponseEntity.ok().build();
				} else if (result == 2) {
					return new ResponseEntity<>("Interrupted I/O operation.", HttpStatus.CONFLICT);
				} else {
					return new ResponseEntity<>("No presentationdrafts available", HttpStatus.NOT_FOUND);
				}
			} catch (PrinterException e) {
				e.printStackTrace();
				return new ResponseEntity<>("A error occured while trying to print file", HttpStatus.BAD_REQUEST);
			}
		}
		return new ResponseEntity<>("Invalid conference ID", HttpStatus.NOT_FOUND);
	}

	@ApiOperation(value = "Get single presentationDraft and create PDF ")
	@ApiResponses({ @ApiResponse(code = 200, message = "Create PDF succesfully"),
			@ApiResponse(code = 400, message = "Invalid conference ID and/or presentationdraft ID value"),
			@ApiResponse(code = 404, message = "No presentationdraft ID available"),
			@ApiResponse(code = 412, message = "Cancelled save request") })
	@GetMapping("api/{conferenceId}/download/pdf/{id}")
	public ResponseEntity<?> getSinglePdf(
			@ApiParam(required = true, name = "id", value = "PresentationDraft ID") @PathVariable("id") Long id,
			@ApiParam(required = true, name = "conferenceId", value = "Conference ID") @PathVariable("conferenceId") Long conferenceId) {
		if (conferenceId > 0 && conferenceId != null) {
			if (id != null && id != 0) {
				int result = pdfService.getSinglePresentationDraftToPDF(id, conferenceId);
				if (result == 1) {
					return ResponseEntity.ok().build();
				} else if (result == 2) {
					return new ResponseEntity<>("Save request was cancelled", HttpStatus.CONFLICT);
				} else {
					return new ResponseEntity<>("Could not find presentationdraft/conference with the given ID",
							HttpStatus.NOT_FOUND);
				}
			}
			return ResponseEntity.badRequest().build();
		}
		return ResponseEntity.badRequest().build();
	}

	@ApiOperation(value = "Get single presentationDraft and print PDF ")
	@ApiResponses({ @ApiResponse(code = 200, message = "Printing PDF succesfully"),
			@ApiResponse(code = 400, message = "A error occured while trying to print file or invalid ID value"),
			@ApiResponse(code = 404, message = "No presentationdraft and/or conference available") })
	@GetMapping("api/{conferenceId}/print/pdf/{id}")
	public ResponseEntity<?> printSinglePdf(
			@ApiParam(required = true, name = "id", value = "PresentationDraft ID") @PathVariable("id") Long id,
			@ApiParam(required = true, name = "conferenceId", value = "Conference ID") @PathVariable("conferenceId") Long conferenceId) {
		if (conferenceId > 0 && conferenceId != null) {
			if (id != null && id != 0) {
				try {
					int result = pdfService.printSinglePdf(id, conferenceId);
					if (result == 1) {
						return ResponseEntity.ok().build();
					} else if (result == 2) {
						return new ResponseEntity<>("Interrupted I/O operation.", HttpStatus.CONFLICT);
					} else {
						return new ResponseEntity<>("Could not find presentationdraft ID", HttpStatus.NOT_FOUND);
					}
				} catch (PrinterException e) {
					e.printStackTrace();
				}
			}
			return ResponseEntity.badRequest().build();
		}
		return ResponseEntity.badRequest().build();
	}
}