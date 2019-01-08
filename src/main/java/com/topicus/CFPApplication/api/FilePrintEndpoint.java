package com.topicus.CFPApplication.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.topicus.CFPApplication.persistence.PdfService;

import io.swagger.annotations.Api;

@RestController
@Api(value = "FilePrintEndpoint", description = "Print files")
public class FilePrintEndpoint {

	private PdfService pdfService;

	@Autowired
	public FilePrintEndpoint(PdfService pdfService) {
		this.pdfService = pdfService;
	}

//	@ApiOperation(value = "Print all presentationDrafts")
//	@ApiResponses({ @ApiResponse(code = 200, message = "Printing PDF succesfully"),
//			@ApiResponse(code = 400, message = "A error occured while trying to print file"),
//			@ApiResponse(code = 404, message = "No presentationdraft available") })
//	@GetMapping("api/print/pdf")
//	public ResponseEntity<?> printAllPdf() {
//		try {
//			int result = pdfService.printAllPdf();
//			if (result == 1) {
//				return ResponseEntity.ok().build();
//			} else if (result == 2) {
//				return new ResponseEntity<>("Interrupted I/O operation.", HttpStatus.CONFLICT);
//			} else {
//				return new ResponseEntity<>("No presentationdrafts available", HttpStatus.NOT_FOUND);
//			}
//		} catch (PrinterException e) {
//			e.printStackTrace();
//			return new ResponseEntity<>("A error occured while trying to print file", HttpStatus.BAD_REQUEST);
//		}
//	}

//	@ApiOperation(value = "Print single presentationDraft")
//	@ApiResponses({ @ApiResponse(code = 200, message = "Printing PDF succesfully"),
//			@ApiResponse(code = 400, message = "A error occured while trying to print file or invalid ID value"),
//			@ApiResponse(code = 404, message = "No presentationdraft available") })
//	@GetMapping("api/print/pdf/{id}")
//	public ResponseEntity<?> printSinglePdf(
//			@ApiParam(required = true, name = "id", value = "PresentationDraft ID") @PathVariable("id") Long id) {
//		if (id != null && id != 0) {
//			try {
//				int result = pdfService.printSinglePdf(id);
//				if (result == 1) {
//					return ResponseEntity.ok().build();
//				} else if (result == 2) {
//					return new ResponseEntity<>("Interrupted I/O operation.", HttpStatus.CONFLICT);
//				} else {
//					return new ResponseEntity<>("Could not find presentationdraft ID", HttpStatus.NOT_FOUND);
//				}
//			} catch (PrinterException e) {
//				e.printStackTrace();
//			}
//		}
//		return ResponseEntity.badRequest().build();
//	}
}
