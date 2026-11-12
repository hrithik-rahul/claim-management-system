package com.claim.notification.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.mail.MailException;

import com.claim.notification.event.ClaimCreatedEvent;

@Service
public class NotificationService {
	 private final JavaMailSender mailSender;

	    public NotificationService(JavaMailSender mailSender) {
	        this.mailSender = mailSender;
	    }
	    public void sendClaimSubmittedNotification(ClaimCreatedEvent event) {
	    	// Check whether customer email is available
	        if (event.getCustomerEmail() == null ||
	            event.getCustomerEmail().isBlank()) {

	            System.out.println("Customer email is missing. Email notification skipped.");
	            return;
	        }

	        SimpleMailMessage message = new SimpleMailMessage();

	        message.setTo(event.getCustomerEmail());

	        message.setSubject("Claim Submitted Successfully");

	        message.setText(
	                "Hello " + event.getCustomerName() + ",\n\n" +
	                "Your claim has been submitted successfully.\n\n" +
	                "Claim ID: " + event.getClaimId() + "\n" +
	                "Claim Type: " + event.getClaimType() + "\n" +
	                "Status: " + event.getStatus() + "\n\n" +
	                "Thank you for using our Claim Management System."
	        );
	        System.out.println(event);

	        try {
	            mailSender.send(message);

	            System.out.println(
	                "Email sent successfully to: " + event.getCustomerEmail()
	            );

	        } catch (MailException e) {

	            System.err.println("Failed to send claim notification email.");
	            System.err.println("Reason: " + e.getMessage());
	        }
	    }
	    // 2. Claim approved/rejected notification
	    public void sendClaimStatusUpdatedNotification(ClaimCreatedEvent event) {

	        if (event.getCustomerEmail() == null ||
	            event.getCustomerEmail().isBlank()) {

	            System.out.println("Customer email missing. Notification skipped.");
	            return;
	        }

	        SimpleMailMessage message = new SimpleMailMessage();

	        String status = event.getStatus();

	        if ("APPROVED".equalsIgnoreCase(status)) {

	            message.setSubject("Your Claim Has Been Approved");

	            message.setText(
	                    "Hello " + event.getCustomerName() + ",\n\n" +
	                    "Good news! Your claim has been approved.\n\n" +
	                    "Claim ID: " + event.getClaimId() + "\n" +
	                    "Claim Type: " + event.getClaimType() + "\n" +
	                    "Status: APPROVED\n\n" +
	                    "Thank you for using our Claim Management System."
	            );

	        } else if ("REJECTED".equalsIgnoreCase(status)) {

	            message.setSubject("Your Claim Has Been Rejected");

	            message.setText(
	                    "Hello " + event.getCustomerName() + ",\n\n" +
	                    "Unfortunately, your claim has been rejected.\n\n" +
	                    "Claim ID: " + event.getClaimId() + "\n" +
	                    "Claim Type: " + event.getClaimType() + "\n" +
	                    "Status: REJECTED\n\n" +
	                    "Please contact our support team for further information."
	            );

	        } else {
	            System.out.println("Unsupported claim status: " + status);
	            return;
	        }

	        sendEmail(message, event.getCustomerEmail());
	    }

	    // Common email sending method
	    private void sendEmail(SimpleMailMessage message, String email) {

	        try {
	        	 message.setTo(email); 
	            mailSender.send(message);

	            System.out.println("Email sent successfully to: " + email);

	        } catch (MailException e) {

	            System.err.println("Failed to send claim notification email.");
	            System.err.println("Reason: " + e.getMessage());
	        }
	    }
}
