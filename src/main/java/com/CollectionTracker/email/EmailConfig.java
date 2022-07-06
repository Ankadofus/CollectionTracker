package com.CollectionTracker.email;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class EmailConfig {
	
	@Bean
	public JavaMailSender getJavaMailSender() {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		
		//fill these in with a valid email that'll serve as the sender
		mailSender.setUsername("qfewqwfqwef@hotmail.com");
		mailSender.setPassword("a5235308");
		
		Properties props = mailSender.getJavaMailProperties();
	    props.put("mail.smtp.auth", "true");
	    props.put("mail.smtp.starttls.enable", "true");
	    props.put("mail.smtp.host", "smtp-mail.outlook.com");
	    props.put("mail.smtp.port", "587");

		
		return mailSender;
	}
	
}
