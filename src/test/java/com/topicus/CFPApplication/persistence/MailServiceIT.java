package com.topicus.CFPApplication.persistence;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetup;
import com.topicus.CFPApplication.domain.MailContentBuilder;

@SpringBootTest
@RunWith(SpringRunner.class)
public class MailServiceIT {
	 
    private GreenMail smtpServer = new GreenMail(new ServerSetup(25, null, "smtp"));
    
    @Autowired
    private MailService mailService;
    
    @Autowired
    private MailContentBuilder templateBuilder;
     
    @Test
    public void shouldSendMail() throws Exception {
    	smtpServer.start();
        //given
        String recipient = "name@dolszewski.com";
        //when
        mailService.sendMail(recipient, "name");
        //then
        assertReceivedMessageContains(templateBuilder.build("name", "demo-template"));
        smtpServer.stop();
    }
     
    private void assertReceivedMessageContains(String expected) throws IOException, MessagingException {
    	MimeMessage[] receivedMessages = smtpServer.getReceivedMessages();
        assertEquals(1, receivedMessages.length);
        String content = receivedMessages[0].getContent().toString().trim();
        assertEquals(expected, content);
    }
	     
}
