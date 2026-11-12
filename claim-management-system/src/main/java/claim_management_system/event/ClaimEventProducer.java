package claim_management_system.event;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class ClaimEventProducer {
    private static final String TOPIC = "claim-created";
    private static final String STATUS_TOPIC = "claim-status-updated";

    private final KafkaTemplate<String, ClaimCreatedEvent> kafkaTemplate;

    public ClaimEventProducer(
            KafkaTemplate<String, ClaimCreatedEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendClaimCreatedEvent(ClaimCreatedEvent event) {

        String key = String.valueOf(event.getClaimId());

        kafkaTemplate.send(TOPIC, key, event);

        System.out.println("Claim event sent to Kafka: " + event.getClaimId());
    }
    public void sendClaimStatusUpdatedEvent(ClaimCreatedEvent event) {
    	 String key = String.valueOf(event.getClaimId());

         kafkaTemplate.send(STATUS_TOPIC, key, event);
         System.out.println(
        	        "Claim status event sent to Kafka: " +
        	        event.getClaimId() + " - " + event.getStatus()
        	    );

    }
    
}
