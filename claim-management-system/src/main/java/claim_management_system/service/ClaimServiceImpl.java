package claim_management_system.service;

import claim_management_system.event.ClaimCreatedEvent;
//import claim_management_system.kafka.ClaimEventProducer;


import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import claim_management_system.event.ClaimEventProducer;
import claim_management_system.model.Claim;
import claim_management_system.model.Customer;
import claim_management_system.repository.ClaimRepo;
import claim_management_system.repository.CustomerRepo;

@Service
public class ClaimServiceImpl implements ClaimService {
	  private final ClaimRepo claimRepository;
	    private final CustomerRepo customerRepository;
	    private final ClaimEventProducer claimEventProducer;

	    public ClaimServiceImpl(ClaimRepo claimRepository,
	                            CustomerRepo customerRepository,
	                            ClaimEventProducer claimEventProducer) {
	    	
	        this.claimRepository = claimRepository;
	        this.customerRepository = customerRepository;
	       this.claimEventProducer=claimEventProducer;
	    }

	    // Create claim for an existing customer
	    @Override
	    @Transactional
	    public Claim createClaim(Long customerId, String claimType,
	                             String description) {

	        Customer customer = customerRepository.findById(customerId)
	                .orElseThrow(() ->
	                    new RuntimeException(
	                        "Customer not found with ID: " + customerId));

	        Claim claim = new Claim();
	        claim.setClaimType(claimType);
	        claim.setDescription(description);
	        claim.setCustomer(customer);
	        
	     // Step 1: Save claim in SQL Server
	        Claim savedClaim = claimRepository.save(claim);

	        // Step 2: Create Kafka event
	        ClaimCreatedEvent event = new ClaimCreatedEvent(
	                savedClaim.getId(),
	                savedClaim.getClaimType(),
	                savedClaim.getDescription(),
	                savedClaim.getStatus(),
	                savedClaim.getSubmittedAt(),
	                customer.getId(),
	                customer.getName(),
	                customer.getEmail(),
	                customer.getPhone()
	        );

	        // Step 3: Publish event to Kafka
	        claimEventProducer.sendClaimCreatedEvent(event);

	        return savedClaim;
	        
	    }
	    // Get all claims for one customer
	    @Override
	    public List<Claim> getClaimsByCustomerId(Long customerId) {

	        if (!customerRepository.existsById(customerId)) {
	            throw new RuntimeException(
	                    "Customer not found with ID: " + customerId);
	        }

	        return claimRepository.findActiveClaimsByCustomerId(customerId);
	    }

	    // Update claim
	    @Override
	    public Claim updateClaim(Long id, String claimType,
	                             String description) {

	        Claim existingClaim = getClaimById(id);

	        existingClaim.setClaimType(claimType);
	        existingClaim.setDescription(description);
//	        existingClaim.setStatus(status);

	        return claimRepository.save(existingClaim);
	    }

	    // Delete claim
	    @Override
	    public void deleteClaim(Long id) {

	        Claim existingClaim = getClaimById(id);
	        existingClaim.setDeleted(true);

	        claimRepository.save(existingClaim);
	    }
	    // ================= MANAGER OPERATIONS =================

	    // Manager can view all active claims
	    @Override
	    public List<Claim> getAllClaims() {
	        return claimRepository.findActiveClaims();
	    }

	    // Manager can view claim by ID
	    @Override
	    public Claim getClaimById(Long id) {

	        return claimRepository.findById(id)
	                .filter(claim -> !claim.isDeleted())
	                .orElseThrow(() ->
	                    new RuntimeException("Claim not found: " + id));
	    }
	 // Manager can update claim status
	    @Override
	    public Claim updateClaimStatus(Long id, String status) {

	        Claim existingClaim = getClaimById(id);

	        // Check whether status has changed
	        if (existingClaim.getStatus().equalsIgnoreCase(status)) {
	            return existingClaim;
	        }

	        existingClaim.setStatus(status);

	        // Step 1: Save updated claim in SQL Server
	        Claim savedClaim = claimRepository.save(existingClaim);

	        // Step 2: Publish Kafka event for APPROVED or REJECTED
	        if (status.equalsIgnoreCase("APPROVED") ||
	            status.equalsIgnoreCase("REJECTED")) {

	            Customer customer = savedClaim.getCustomer();

	            ClaimCreatedEvent event = new ClaimCreatedEvent(
	                    savedClaim.getId(),
	                    savedClaim.getClaimType(),
	                    savedClaim.getDescription(),
	                    savedClaim.getStatus(),
	                    savedClaim.getSubmittedAt(),
	                    customer.getId(),
	                    customer.getName(),
	                    customer.getEmail(),
	                    customer.getPhone()
	            );

	            claimEventProducer.sendClaimStatusUpdatedEvent(event);
	        }

	        return savedClaim;
	    }

	 
	 

	   
}
