package com.topicus.CFPApplication.persistence;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.StatusType;

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
    	emailSender.send(prepareAndSend(email, name,"demo-template"));
    }
	
	public Iterable<Applicant> sendMail(long id, String templateName){
		Optional<PresentationDraft> result = presentationDraftService.findById(id);
		List<Applicant> couldNotSendList = new ArrayList<>();
		if(result.isPresent()) {
			for(Applicant d : result.get().getApplicants()) {
				if(d.getEmail() != null && d.getEmail().length() > 1) {
					emailSender.send(prepareAndSend(d.getEmail(), d.getName(),templateName));
				}else {
					couldNotSendList.add(d);
				}
			}
			return couldNotSendList; // all mails have been send if this list is empty. If not, those applicants are returned.
		}
		return null; // if this is send. The presentationDraft id doesn't exist.
	}
	
	public List<Applicant> sendAllApplicantsMail(String templateName){
		Iterable<Applicant> applicants = applicantService.findAll();
		List<Applicant> couldNotSendMail = new ArrayList<>();
		for(Applicant applicant : applicants) {
			try {
				emailSender.send(prepareAndSend(applicant.getEmail(), applicant.getName(), templateName));
			} catch (MailException e) {
				couldNotSendMail.add(applicant);
			} 
		}
		return couldNotSendMail;
	}
	
   public MimeMessagePreparator prepareAndSend(String recipient, String recipientName, String templateName){
    	MimeMessagePreparator messagePreparator = mimeMessage -> {
    		MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
    		String content = mailContentBuilder.build(recipientName, templateName);
    		messageHelper.setTo(recipient);
    		messageHelper.setSubject(templateName);
    		messageHelper.setText(content, true);
    	};
    		return messagePreparator;
    }
    
    public Iterable<String> getConfigValues(){
    	List<String> setupList = new ArrayList<>();
		Collections.addAll(setupList, emailSender.getHost(),""+emailSender.getPort(),emailSender.getUsername());
		return setupList;
    }
    
    public StatusType setupConfig(String host, int port, String username, String password) {
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
        	emailSender.send(prepareAndSend(username, "organisator","demo-template"));
        }catch(MailSendException e) {
        	return Response.status(412,"If it took more than 2 secs, than the port is incorrect. Else the host server is incorrect").build().getStatusInfo();
        }
        catch(Exception e) {
        	return Response.status(412,"the username or password is incorrect").build().getStatusInfo();
        }
        return Response.ok().build().getStatusInfo();
    }

}
