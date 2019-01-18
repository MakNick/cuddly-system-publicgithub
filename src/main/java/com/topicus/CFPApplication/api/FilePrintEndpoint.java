package com.topicus.CFPApplication.api;

import java.awt.print.PrinterException;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.topicus.CFPApplication.persistence.pdf.PdfService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import rst.pdfbox.layout.elements.Document;

@RestController
@Api(value = "FilePrintEndpoint", description = "Print files")
public class FilePrintEndpoint {

	private PdfService pdfService;

	@Autowired
	public FilePrintEndpoint(PdfService pdfService) {
		this.pdfService = pdfService;
	}

	@ApiOperation(value = "Print all presentationDrafts")
	@ApiResponses({ @ApiResponse(code = 200, message = "Printing PDF succesfully"),
			@ApiResponse(code = 400, message = "A error occured while trying to print file"),
			@ApiResponse(code = 404, message = "No presentationdraft and/or conference available") })
	@GetMapping("api/{conferenceId}/print/pdf")
	public ResponseEntity<?> printAllPdf(
			@ApiParam(required = true, name = "conferenceId", value = "Conference ID") @PathVariable("conferenceId") Long conferenceId) {
		if (conferenceId > 0 && conferenceId != null) {
			try {
				Document result = pdfService.printAllPdf(conferenceId);
				if (result != null) {
					return ResponseEntity.ok().build();
				} else {
					return new ResponseEntity<>("No presentationdrafts available", HttpStatus.NOT_FOUND);
				}
			} catch (PrinterException e) {
				e.printStackTrace();
				return new ResponseEntity<>("A error occured while trying to print file", HttpStatus.BAD_REQUEST);
			} catch (IOException e) {
				return new ResponseEntity<>("I/O Exception", HttpStatus.CONFLICT);
			}
		}
		return new ResponseEntity<>("No conference available", HttpStatus.NOT_FOUND);
	}

	@ApiOperation(value = "Print single presentationDraft")
	@ApiResponses({ @ApiResponse(code = 200, message = "Printing PDF succesfully"),
			@ApiResponse(code = 400, message = "A error occured while trying to print file or invalid ID value"),
			@ApiResponse(code = 404, message = "No presentationdraft and/or conference available") })
	@GetMapping("api/print/pdf/{id}")
	public ResponseEntity<?> printSinglePdf(
			@ApiParam(required = true, name = "id", value = "PresentationDraft ID") @PathVariable("id") Long id,
			@ApiParam(required = true, name = "conferenceId", value = "Conference ID") @PathVariable("conferenceId") Long conferenceId) {
		if (conferenceId > 0 && conferenceId != null) {
			if (id != null && id != 0) {
				try {
					Document result = pdfService.printSinglePdf(id, conferenceId);
					if (result != null) {
						return ResponseEntity.ok().build();
					} else {
						return new ResponseEntity<>("No presentationdrafts available", HttpStatus.NOT_FOUND);
					}
				} catch (PrinterException e) {
					e.printStackTrace();
					return new ResponseEntity<>("A error occured while trying to print file", HttpStatus.BAD_REQUEST);
				} catch (IOException e) {
					return new ResponseEntity<>("I/O Exception", HttpStatus.CONFLICT);
				}
			} else {
				return new ResponseEntity<>("No presentationdrafts available", HttpStatus.NOT_FOUND);
			}
		}
		return new ResponseEntity<>("No conference available", HttpStatus.NOT_FOUND);
	}
}