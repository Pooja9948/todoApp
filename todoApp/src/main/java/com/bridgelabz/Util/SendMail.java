package com.bridgelabz.Util;

import org.springframework.mail.MailSender;

import org.springframework.mail.SimpleMailMessage;  

/**
 * @author Pooja todoApp
 *
 */
public class SendMail {
	
	private MailSender mailSender;  
	   
    /**
     * @param mailSender
     */
    public void setMailSender(MailSender mailSender) {  
        this.mailSender = mailSender;  
    }
    
    
    
    /**
     * @param from
     * @param to
     * @param subject
     * @param msg
     * @return
     * sending the mail
     */
    public boolean sendMail(String from, String to, String subject, String msg) {  
        //creating message  
        SimpleMailMessage message = new SimpleMailMessage();  
        message.setFrom(from);  
        message.setTo(to);  
        message.setSubject(subject);  
        message.setText(msg);  
        //sending message  
        mailSender.send(message);     
        return true;
    }
}
