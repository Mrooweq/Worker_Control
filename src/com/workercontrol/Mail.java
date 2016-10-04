package com.workercontrol;


import java.util.Date;
import java.util.Properties;

import javax.activation.CommandMap;
import javax.activation.MailcapCommandMap;
import javax.mail.BodyPart;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;


public class Mail extends javax.mail.Authenticator { 
	private String user; 
	private String pass; 
	private String[] to; 
	private String from; 
	private String port; 
	private String sport; 
	private String host; 
	private String subject; 
	private String body; 
	private boolean auth; 
	private boolean debuggable; 
	private Multipart multipart; 


	public Mail(String user, String pass) { 
		this.host = "smtp.gmail.com";
		this.port = "465"; 
		this.sport = "465"; 
		this.user = ""; 
		this.pass = ""; 
		this.from = ""; 
		this.subject = ""; 
		this.body = ""; 
		this.debuggable = false; 
		this.auth = true;
		this.multipart = new MimeMultipart(); 
		this.user = user; 
		this.pass = pass; 

		MailcapCommandMap mc = (MailcapCommandMap) CommandMap.getDefaultCommandMap(); 
		mc.addMailcap("text/html;; x-java-content-handler=com.sun.mail.handlers.text_html"); 
		mc.addMailcap("text/xml;; x-java-content-handler=com.sun.mail.handlers.text_xml"); 
		mc.addMailcap("text/plain;; x-java-content-handler=com.sun.mail.handlers.text_plain"); 
		mc.addMailcap("multipart/*;; x-java-content-handler=com.sun.mail.handlers.multipart_mixed"); 
		mc.addMailcap("message/rfc822;; x-java-content-handler=com.sun.mail.handlers.message_rfc822"); 
		CommandMap.setDefaultCommandMap(mc); 
	} 

	public boolean send() throws Exception { 
		Properties props = setProperties(); 

		if(!user.equals("") && !pass.equals("") && to.length > 0 && !from.equals("") && !subject.equals("") && !body.equals("")) { 
			Session session = Session.getInstance(props, this); 

			MimeMessage msg = new MimeMessage(session); 

			msg.setFrom(new InternetAddress(from)); 

			InternetAddress[] addressTo = new InternetAddress[to.length]; 
			for (int i = 0; i < to.length; i++) { 
				addressTo[i] = new InternetAddress(to[i]); 
			} 
			msg.setRecipients(MimeMessage.RecipientType.TO, addressTo); 

			msg.setSubject(subject); 
			msg.setSentDate(new Date()); 

			BodyPart messageBodyPart = new MimeBodyPart(); 
			messageBodyPart.setText(body); 
			multipart.addBodyPart(messageBodyPart); 

			msg.setContent(multipart); 

			Transport.send(msg); 

			return true; 
		} else { 
			return false; 
		} 
	} 


	@Override 
	public PasswordAuthentication getPasswordAuthentication() { 
		return new PasswordAuthentication(user, pass); 
	} 

	private Properties setProperties() { 
		Properties props = new Properties(); 

		props.put("mail.smtp.host", host); 

		if(debuggable) { 
			props.put("mail.debug", "true"); 
		} 

		if(auth) { 
			props.put("mail.smtp.auth", "true"); 
		} 

		props.put("mail.smtp.port", port); 
		props.put("mail.smtp.socketFactory.port", sport); 
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory"); 
		props.put("mail.smtp.socketFactory.fallback", "false"); 

		return props; 
	} 

	public String getBody() { 
		return body; 
	} 

	public void setBody(String _body) { 
		this.body = _body; 
	} 

	public void setTo(String[] toArr) {
		this.to = toArr;
	}

	public void setFrom(String string) {
		this.from = string;
	}

	public void setSubject(String string) {
		this.subject = string;
	}
} 
