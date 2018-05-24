package com.project.Rentingaccommodation.utils;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang.RandomStringUtils;

public class SendMail {
	
	public static String sendEmail(String email){
		
		// Recipient's email ID needs to be mentioned.
	      String to = email;
	      System.out.println("eee " + email);
	      // Sender's email ID needs to be mentioned
	      String from = "xml.mail.project@gmail.com";
	      final String username = "xml.mail.project@gmail.com";//change accordingly
		  final String password = "xmlpassword";//change accordingly 

	      // Assuming you are sending email from localhost
	      String host = "smtp.gmail.com";

	      // Get system properties
	      Properties properties = new Properties();
	      properties.put("mail.smtp.auth", "true");
	      properties.put("mail.smtp.starttls.enable", "true");
	      properties.put("mail.smtp.host", host);
	      properties.put("mail.smtp.port", "587");
	      Session.getInstance(properties, null);
	      // Setup mail server
	     

	      // Get the default Session object.
	      Session session = Session.getDefaultInstance(properties);

	      try {
	         // Create a default MimeMessage object.
	         MimeMessage message = new MimeMessage(session);

	         // Set From: header field of the header.
	         message.setFrom(new InternetAddress(from));

	         // Set To: header field of the header.
	         message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

	         // Set Subject: header field
	         message.setSubject("Password reset");

	         // Now set the actual message
	         String link = "To reset your password click <a href=\"http://localhost:4200/reset" + to + "\">here.</a>";
	         
	         String randomPassword = generateRandomPassword();
	         
	         message.setText(link + "/n New password " + randomPassword, "UTF-8", "html");
//	         message.setText("<a href=\"http://localhost:9000/#/successfullRegistration\">ACTIVAR CUENTA</a>");

	         // Send message
	         Transport.send(message, username, password);
	         System.out.println("Sent message successfully....");
	         
	         return randomPassword;
	         
	      } catch (MessagingException mex) {
	         mex.printStackTrace();
	      }
	     
		return null;
	}

	public static String generateRandomPassword() {
		  
	    int length = 10;
	    boolean useLetters = true;
	    boolean useNumbers = false;
	    String generatedPassword = RandomStringUtils.random(length, useLetters, useNumbers);
	 
	    System.out.println(generatedPassword);
	    return generatedPassword;
	}
}
