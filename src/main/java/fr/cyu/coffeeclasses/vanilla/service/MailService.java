package fr.cyu.coffeeclasses.vanilla.service;

import fr.cyu.coffeeclasses.vanilla.entity.user.User;

import jakarta.mail.*;
import jakarta.mail.internet.*;
import jakarta.mail.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class MailService {
	// Properties
	private final Session session;
	private final static String fromEmail = "coffeeclasses.service@gmail.com";
	private final static String password = "zyhd gunf qwjc bfwd";
	// Singleton
	private final static MailService instance = new MailService();
	public static MailService getInstance() {
		return instance;
	}
	// Logger
	private static final Logger logger = LoggerFactory.getLogger(MailService.class);
	
	private MailService() {
		// Mail server properties
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		// Mail session with authentication
		session = Session.getInstance(props, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(fromEmail, password);
			}
		});
	}
	
	public void sendMail(User dest, String subject, String body) {
		try {
			// Create a new email message
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(fromEmail, "CoffeeClasses"));
			message.setRecipient(
					Message.RecipientType.TO,
					new InternetAddress(dest.getEmail(), dest.getLastName() + " " + dest.getFirstName())
			);
			message.setSubject(subject);
			message.setText(body);

			// Send the message
			Transport.send(message);
		} catch (Exception e) {
			logger.error("Error while sending email: {}", e.getMessage());
		}
    }
}
