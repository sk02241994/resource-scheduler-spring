package com.office.resourcescheduler.service;

import java.io.IOException;

import javax.activation.DataHandler;
import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.office.resourcescheduler.util.CalenderEvent;

@Service
public class EmailService {

	private JavaMailSender javaMailSender;

	@Autowired
	public EmailService(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}

	public String sendMail(String[] to, String subject, String text) {
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setTo(to);
		mailMessage.setSubject(subject);
		mailMessage.setText(text);

		javaMailSender.send(mailMessage);

		return "Mail has been successfully sent.";
	}

	public void sendIcsAttachment(String to, String subject, String text, Long reservationId)
			throws MessagingException, IOException {
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
		messageHelper.setTo(to);
		messageHelper.setSubject(subject);
		messageHelper.setText(text);
		
		MimeBodyPart messageBodyPart = new MimeBodyPart();
		messageBodyPart.setHeader("Content-Class", "urn:content-classes:calendarmessage");
		messageBodyPart.setHeader("Content-ID", "calendar_message");
		messageBodyPart
				.setDataHandler(new DataHandler(new ByteArrayDataSource(CalenderEvent.getAttachment(reservationId, to),
						"text/calendar;method=REQUEST;name=\"invite.ics\"")));
		MimeMultipart multipart = new MimeMultipart();
		multipart.addBodyPart(messageBodyPart);
		mimeMessage.setContent(multipart);
		javaMailSender.send(mimeMessage);
	}
}
