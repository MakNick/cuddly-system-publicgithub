package com.topicus.CFPApplication.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.topicus.CFPApplication.domain.Applicant;
import com.topicus.CFPApplication.persistence.MailService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@Api(value="MailEndpoint", description="Sends and configures mail settings")
public class MailEndpoint {
	
	private MailService mailService;
	
	@Autowired
	public MailEndpoint(MailService mailService) {
		this.mailService = mailService;
	}
	
	/*
	 * gives information on all the mail configuration values. Will give current mail server,port,username.
	 * 
	 * @return Iterable<String>
	 */
	@ApiOperation("Gives information on the current: server host, port and sender's e-mail address")
	@GetMapping("api/configs")
	public ResponseEntity<Iterable<String>> getConfigValues() {
		return ResponseEntity.ok(mailService.getConfigValues());
	}
	
	/*
	 * sets the mail server, port, username and password. Values should be given in that order.
	 * If setup was successful, it will return a OK status. If not it will return a 412 status, with the reason.
	 * After successfully setting up the email, a template will be send to the email address that it was successful.
	 * 
	 * @return Response
	 */
	@ApiOperation("Sets the email server, port, sender's e-mail and password."
			+ "The e-mail must exists on the e-mail server in combination with the given password")
	@ApiResponses({
        @ApiResponse(code = 200, message = "Setup successful"),
        @ApiResponse(code = 400, message = "1. Name or password is incorrect. 2. E-mail server or port is incorrect"
        		+ "If it takes longer than 2 seconds to get a response, then the port is wrong")
    })
	@GetMapping("api/setupconfig/{host}/{port}/{username}/{password}")
	public ResponseEntity setupConfig(
			@ApiParam(required = true, name = "host", value = "E-mail server", type="String")
			@PathVariable(name="host")String host, 
			@ApiParam(required = true, name = "port", value = "Server port", type="Integer")
			@PathVariable(name="port")Integer port,
			@ApiParam(required = true, name = "username", value = "Existing e-mail on the given e-mail server", type="String")
			@PathVariable(name="username")String username, 
			@ApiParam(required = true, name = "password", value = "Password of the given e-mail", type="String")
			@PathVariable(name="password")String password){
			int result = mailService.setupConfig(host,port,username,password);
		if(result == 1) {
			return ResponseEntity.ok().build();
		}else if(result == 2) {
			return new ResponseEntity<>("The port might be incorrect if the response took longer than 2 seconds. "
        			+ "Else the host server is incorrect", HttpStatus.BAD_REQUEST);
		}else {
			return new ResponseEntity<>("the username or password is incorrect", HttpStatus.BAD_REQUEST);
		}
	}
	
	/*
	 * for testing purposes!
	 */

	@ApiOperation("For testing purposes. Will send a mail to the given e-mail. Be sure that you have setup the e-mail configurations with the setupconfig endpoint")
	@GetMapping("api/sendmail/{email}/{name}")
	public void sendMail(
			@ApiParam(required = true, name = "email", value = "E-mail receiver", type="String")
			@PathVariable("email") String email, 
			@ApiParam(required = true, name = "name", value = "Name receiver", type="String")
			@PathVariable("name")String name) {
        mailService.sendMail(email,name);
	}
	
	@ApiOperation("For testing purposes. Will send a mail to the given e-mail. Be sure that you have setup the e-mail configurations with the setupconfig endpoint."
			+ "This will also insert a given text into the e-mail")
	@GetMapping("api/sendmailtext/{email}/{name}/{text}")
	public void sendMailText(
			@ApiParam(required = true, name = "email", value = "E-mail receiver", type="String")
			@PathVariable("email") String email, 
			@ApiParam(required = true, name = "name", value = "Name receiver", type="String")
			@PathVariable("name") String name,
			@ApiParam(required = true, name = "text", value = "E-mail content", type="String")
			@PathVariable("text") String text) {
        mailService.sendMailText(email,name, text);
	}
	
	/* this will find a presentationDraft by id, and then send all the applicants in the set of the applicationDraf an email
	 * the subject of the mail will be set to the name of the email template.html
	 * if an applicant does not have an email(null), or the email field has an String length less than 2. He will be added to the couldNotSendList.
	 * when this list is returned empty. It means that it has successfully send all the applicants an e-mail. If an applicant does not have an email
	 * he will be added to the list, and the list will be returned with that applicant.
	 * 
	 * @return Iterable<Applicant>
	 */
	@ApiOperation("Sends mail to all applicants of a given presentationdraft. You can only send HTML templates that are in the src/main/resources/templates folder")
	@GetMapping("api/sendmail/{id}/template/{templateName}")
	public ResponseEntity<Iterable<Applicant>> sendMail(
			@ApiParam(required = true, name = "id", value = "presentationdraft ID", type="Long")
			@PathVariable(name = "id")Long id, 
			@ApiParam(required = true, name = "templateName", value = "name template", type="String")
			@PathVariable(name ="templateName")String templateName) {
		Iterable<Applicant> couldNotSendList = mailService.sendMail(id,templateName);
		return ((List<Applicant>)couldNotSendList).size() > 0 ? ResponseEntity.status(HttpStatus.CONFLICT).body(couldNotSendList) 
				: ResponseEntity.status(HttpStatus.OK).body(couldNotSendList);
	}
	
	/* 
	 * @return List<Applicant>
	 */
	@ApiOperation("Sends mail to all applicants in the database. You can only send HTML templates that are in the src/main/resources/templates folder")
	@GetMapping("api/sendallmail/template/{templateName}")
	public ResponseEntity<Iterable<Applicant>> sendAllMail(
			@ApiParam(required = true, name = "templateName", value = "name template", type="String")
			@PathVariable("templateName") String templateName){
			Iterable<Applicant> couldNotSendList = mailService.sendAllApplicantsMail(templateName);
		return ((List<Applicant>)couldNotSendList).size() > 0 ? ResponseEntity.status(HttpStatus.CONFLICT).body(couldNotSendList) 
				: ResponseEntity.status(HttpStatus.OK).body(couldNotSendList);
	}

}
