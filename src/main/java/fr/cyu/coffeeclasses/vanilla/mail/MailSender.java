package fr.cyu.coffeeclasses.vanilla.mail;

import org.simplejavamail.api.email.Email;
import org.simplejavamail.email.EmailBuilder;

import org.simplejavamail.mailer.MailerBuilder;

import fr.cyu.coffeeclasses.vanilla.entity.user.User;

import org.simplejavamail.api.mailer.Mailer;
import org.simplejavamail.api.mailer.config.TransportStrategy;

public class MailSender {
	private Mailer mailer;
	private String from;
	private String password;
	private final static MailSender instance = new MailSender();
	
	private MailSender() {
		from = "coffeeclasses.service@gmail.com";
        password = "zyhd gunf qwjc bfwd"; //Coffee.classes.123 zyhd gunf qwjc bfwd
		mailer = MailerBuilder
                .withSMTPServer("smtp.gmail.com", 587, from, password)
                .withTransportStrategy(TransportStrategy.SMTP_TLS)
                .buildMailer();
	}
	
	public void sendMail(User dest, String subject, String body) {
		try {
	        Email email = EmailBuilder.startingBlank()
	                .from("Coffee-Classes", this.from)
	                .to(dest.getLastName()+" "+dest.getFirstName(),dest.getEmail())
	                .withSubject(subject)
	                .withPlainText(body)
	                .buildEmail();
	        this.mailer.sendMail(email);}
		catch(Exception e) {
			System.out.println(" Erro while sending mail !");
		}
    }
	
	public static MailSender getInstance() {
		return instance;
	}
	
	public static void main(String[] args) {
		MailSender.getInstance().sendMail(null, null, "test");
	}
}
