package com.claim.notification.event;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.claim.notification.service.NotificationService;

@Component
public class ClaimCreatedEventListener {
	   private final NotificationService notificationService;

	    // Constructor injection
	    public ClaimCreatedEventListener(NotificationService notificationService) {
	        this.notificationService = notificationService;
	    }
	  @KafkaListener(
		        topics = "claim-created",
		        groupId = "notification-group"
		    )
	  public void consumeClaimCreatedEvent(ClaimCreatedEvent event) {

		  System.out.println("===== NEW CLAIM RECEIVED =====");

	        notificationService.sendClaimSubmittedNotification(event);
		    }
	// 2. Listen for claim approval/rejection
	  @KafkaListener(
	          topics = "claim-status-updated",
	          groupId = "notification-group"
	  )
	  public void consumeClaimStatusUpdatedEvent(ClaimCreatedEvent event) {

	      System.out.println("===== CLAIM STATUS UPDATED =====");

	      System.out.println("Claim ID: " + event.getClaimId());
	      System.out.println("Status: " + event.getStatus());

	      notificationService.sendClaimStatusUpdatedNotification(event);
	  }
}
