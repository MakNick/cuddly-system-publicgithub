package com.topicus.CFPApplication.api;

import java.util.List;

import javax.ws.rs.core.Response.StatusType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.topicus.CFPApplication.domain.Applicant;
import com.topicus.CFPApplication.persistence.MailService;

@RestController
public class MailEndpoint {

	@Autowired
	private MailService mailService;

	/*
	 * gives information on all the mail configuration values. Will give current
	 * mail server,port,username and email.
	 * 
	 * @return Iterable<String>
	 */
	@GetMapping("/configs")
	public Iterable<String> getConfigValues() {
		return mailService.getConfigValues();
	}

	/*
	 * sets the mail server, port, username and password. Values should be given in
	 * that order. If setup was successful, it will return a OK status. If not it
	 * will return a 412 status, with the reason. After successfully setting up the
	 * email, a template will be send to the email address that it was successful.
	 * 
	 * @return statusType
	 */
	@GetMapping("/setupconfig/{host}/{port}/{username}/{password}")
	public StatusType setupConfig(@PathVariable(name = "host") String host, @PathVariable(name = "port") int port,
			@PathVariable(name = "username") String username, @PathVariable(name = "password") String password) {
		return mailService.setupConfig(host, port, username, password);
	}

	/*
	 * for testing purposes!
	 */

	@GetMapping("/sendmail/{email}/{name}")
	public void sendmail(@PathVariable(name = "email") String email, @PathVariable(name = "name") String name) {
		mailService.sendMail(email, name);
	}

	/*
	 * this will find a presentationDraft by id, and then send all the applicants in
	 * the set of the applicationDraf an email the subject of the mail will be set
	 * to the name of the email template.html if an applicant does not have an
	 * email(null), or the email field has an String length less than 2. He will be
	 * added to the couldNotSendList. when this list is returned empty. It means
	 * that it has successfully send all the applicants an e-mail. If an applicant
	 * does not have an email he will be added to the list, and the list will be
	 * returned with that applicant.
	 * 
	 * @return empty or full list of applicants
	 */
	@GetMapping("/sendmail/{id}/template/{templateName}")
	public Iterable<Applicant> sendMail(@PathVariable(name = "id") long id,
			@PathVariable(name = "templateName") String templateName) {
		return mailService.sendMail(id, templateName);
	}

	@GetMapping("/sendallmail/template/{templateName}")
	public List<Applicant> sendAllMail(@PathVariable("templateName") String templateName) {
		return mailService.sendAllApplicantsMail(templateName);
	}

}
