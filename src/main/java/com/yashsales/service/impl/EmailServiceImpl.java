package com.yashsales.service.impl;

import java.util.HashMap;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import com.yashsales.constants.ApplicationConstants;
import com.yashsales.entity.User;
import com.yashsales.repository.UserRepository;

@Service
public class EmailServiceImpl  {

	@Autowired
    private JavaMailSender emailSender;

	@Autowired
	private UserRepository userRepo;
	
	
    public HashMap<String, String> sendSimpleMessage(String to) {
     
    	HashMap<String, String> returnMap = new HashMap<String, String>();
    	returnMap.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_FAILURE);
    	returnMap.put("message", "Entered username not found in system.");
    	
    	if(to != null && to.length() > 0) 
    	{
    		User user = userRepo.findByUsername(to);
    		if(user != null && user.getUserId() > 0) {
    			String subject = "Yash Electricals - System Credentials - " + user.getFullName();
    	    	
    	    	StringBuilder sb = new StringBuilder("");
    	    	
   
				/*
				 * sb.append("<style type='text/css'>");
				 * sb.append(".container {margin:50px auto;width:70%;padding:20px 0}");
				 * sb.append(".branding {border-bottom:1px solid #eee}"); sb.
				 * append(".branding a {font-size:1.4em;color: #00466a;text-decoration:none;font-weight:600}"
				 * ); sb.append(".welcome  {font-size:1.1em}");
				 * sb.append(".regards{font-size:0.9em;}"); sb.append("</style>");
				 * 
				 * 
				 * sb.append("</head>"); sb.append("<body>");
				 * 
				 * sb.append("<div class='container'>"); sb.append("<div class='branding'>");
				 * sb.
				 * append("<a href='#' style='font-size:1.4em;color: #00466a;text-decoration:none;font-weight:600'>Yash Electricals</a>"
				 * ); sb.append("</div>"); sb.append("<p class='welcome'>Hi, " +
				 * user.getFullName()+ " </p>"); sb.append("<p>Your Requested Details</p>");
				 * sb.append("<div>Username: " + user.getUsername() + "</div>");
				 * sb.append("<div>Password: 0000</div>");
				 * 
				 * sb.append("<p class='regards'>Regards,<br />Yash Electricals</p>");
				 * sb.append("<hr style='border:none;border-top:1px solid #eee' />");
				 * 
				 * sb.append("</div>");
				 */
    
    	    	sb.append("Yash Electricals\n \n");
    	    	sb.append("Your Requested Details - \n");
    	    	sb.append("Username: " + user.getUsername() + "\n");
    	    	sb.append("Password: 0000 " + "\n");
    	    	sb.append("\n \n");
    	    	sb.append("Regards, Yash Electricals");
    	    	
        	    	MimeMessagePreparator preparator = new MimeMessagePreparator() {
        				public void prepare(MimeMessage mimeMessage) throws Exception {
        					mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
        					mimeMessage.setSubject(subject);
        					mimeMessage.setText(sb.toString());
        				}
        			};
        			try {
        				emailSender.send(preparator);
        				returnMap.put("responseStatus", ApplicationConstants.ResponseConstants.RESPONSE_SUCCESS);
            	    	returnMap.put("message", "Account Details has been sent to your email.");
            	        System.out.println("Email Sent.");
        			} catch (Exception e) {
        				e.printStackTrace();
        			}
        	    	
    		} 
    		
    	}
    	
       return returnMap;
    }
	
}
