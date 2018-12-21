package com.topicus.CFPApplication.persistence;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import com.topicus.CFPApplication.domain.Applicant;
import com.topicus.CFPApplication.domain.MailContentBuilder;
import com.topicus.CFPApplication.domain.PresentationDraft;

@Service
public class MailService {

	@Autowired
	private MailContentBuilder mailContentBuilder;

	@Autowired
	private JavaMailSenderImpl emailSender;

	@Autowired
	private PresentationDraftService presentationDraftService;

	@Autowired
	private ApplicantService applicantService;

	public void sendMail(String email, String name) {
		emailSender.send(prepareAndSend(email, name, "demo-template"));
	}

	public void sendMailText(String email, String name, String text) {
		emailSender.send(prepareAndSend(email, name, text, "demo-template-text"));
	}

	public Iterable<Applicant> sendMail(long id, String templateName) {
		Optional<PresentationDraft> result = presentationDraftService.findById(id);
		List<Applicant> couldNotSendList = new ArrayList<>();
		if (result.isPresent()) {
			for (Applicant d : result.get().getApplicants()) {
				try {
					emailSender.send(prepareAndSend(d.getEmail(), d.getName(), templateName));
				} catch (MailException e) {
					couldNotSendList.add(d);
				}
			}
			return couldNotSendList; // all mails have been send if this list is empty. If not, those applicants are
										// returned.
		}
		return couldNotSendList; // if this is send empty. The presentationDraft id doesn't exist.
	}

	public List<Applicant> sendAllApplicantsMail(String templateName) {
		Iterable<Applicant> applicants = applicantService.findAll();
		List<Applicant> couldNotSendMail = new ArrayList<>();
		for (Applicant applicant : applicants) {
			try {
				emailSender.send(prepareAndSend(applicant.getEmail(), applicant.getName(), templateName));
			} catch (MailException e) {
				couldNotSendMail.add(applicant);
			}
		}
		return couldNotSendMail;
	}

	private MimeMessagePreparator prepareAndSend(String recipient, String recipientName, String templateName) {
		MimeMessagePreparator messagePreparator = mimeMessage -> {
			MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
			String content = mailContentBuilder.build(recipientName, templateName);
			messageHelper.setTo(recipient);
			messageHelper.setSubject(templateName);
			messageHelper.setText(content, true);
		};
		return messagePreparator;
	}

	private MimeMessagePreparator prepareAndSend(String recipient, String recipientName, String text,
			String templateName) {
		MimeMessagePreparator messagePreparator = mimeMessage -> {
			MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
			String content = mailContentBuilder.buildText(recipientName, text, templateName);
			messageHelper.setTo(recipient);
			messageHelper.setSubject(templateName);
			messageHelper.setText(content, true);
		};
		return messagePreparator;
	}

	public Iterable<String> getConfigValues() {
		List<String> setupList = new ArrayList<>();
		Collections.addAll(setupList, emailSender.getHost(), "" + emailSender.getPort(), emailSender.getUsername());
		return setupList;
	}

	public int setupConfig(String host, int port, String username, String password) {
		Properties prop = new Properties();
		prop.setProperty("mail.stmp.auth", "true");
		prop.setProperty("mail.smtp.starttls.enable", "true");
		emailSender.setHost(host);
		emailSender.setPort(port);
		emailSender.setUsername(username);
		emailSender.setPassword(password);
		emailSender.setProtocol("smtp");
		emailSender.setJavaMailProperties(prop);

		// change template to send config setup successfully template.
		try {
			emailSender.send(prepareAndSend(username, "organisator", "demo-template"));
			return 1;
		} catch (MailSendException e) {
			return 2;
		} catch (Exception e) {
			return 3;
		}
	}

}
