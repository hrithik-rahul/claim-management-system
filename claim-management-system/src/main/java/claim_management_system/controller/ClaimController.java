package claim_management_system.controller;

import java.util.List;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import claim_management_system.entity.User;
import claim_management_system.model.Claim;
import claim_management_system.model.Customer;
import claim_management_system.repository.UserRepo;
import claim_management_system.service.ClaimService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/claims")
public class ClaimController {
	 private final ClaimService claimService;
	 private final UserRepo userRepository;

	    public ClaimController(ClaimService claimService,UserRepo userRepository) {
	        this.claimService = claimService;
	        this.userRepository = userRepository;
	    }

	    // Create claim for a customer
	    @PostMapping("/createClaim")
	    public ResponseEntity<Claim> createClaim(
	    		Authentication authentication,
	            @RequestBody Claim claim) {

	    	User user = userRepository.findByUsername(authentication.getName())
	                .orElseThrow(() ->
	                        new RuntimeException("Authenticated user not found"));

	        Customer customer = user.getCustomer();

	        if (customer == null) {
	            throw new IllegalStateException(
	                    "No customer is linked to the logged-in user");
	        }

	        Claim savedClaim = claimService.createClaim(
	                customer.getId(),
	                claim.getClaimType(),
	                claim.getDescription());

	        return ResponseEntity.status(HttpStatus.CREATED)
	                .body(savedClaim);
	    }
	    @GetMapping("/myClaims")
	    public ResponseEntity<List<Claim>> getMyClaims(
	            Authentication authentication) {

	        User user = userRepository.findByUsername(authentication.getName())
	                .orElseThrow(() ->
	                        new RuntimeException("User not found"));

	        Customer customer = user.getCustomer();

	        if (customer == null) {
	            throw new RuntimeException("No customer linked to this user");
	        }

	        return ResponseEntity.ok(
	                claimService.getClaimsByCustomerId(customer.getId())
	        );
	    }
	    // Get all claims
	    @GetMapping("/fetchClaim")
	    public ResponseEntity<List<Claim>> getAllClaims() {
	        return ResponseEntity.ok(claimService.getAllClaims());
	    }

	    // Get claim by ID
	    @GetMapping("/fetchClaimById/{id}")
	    public ResponseEntity<Claim> getClaimById(
	            @PathVariable Long id) {

	        return ResponseEntity.ok(claimService.getClaimById(id));
	    }

	    // Get all claims belonging to a customer
	    @GetMapping("/fetchAllClaimByCustomer/{customerId}")
	    public ResponseEntity<List<Claim>> getClaimsByCustomerId(
	            @PathVariable Long customerId) {

	        return ResponseEntity.ok(
	                claimService.getClaimsByCustomerId(customerId));
	    }

	    // Update claim
	    @PutMapping("updateClaimById/{id}")
	    public ResponseEntity<Claim> updateClaim(
	            @PathVariable Long id,
	            @RequestBody Claim claim) {

	        Claim updatedClaim = claimService.updateClaim(
	                id,
	                claim.getClaimType(),
	                claim.getDescription()
	               );

	        return ResponseEntity.ok(updatedClaim);
	    }

	    // Delete claim
	    @DeleteMapping("deleteClaim/{id}")
	    public ResponseEntity<Void> deleteClaim(
	            @PathVariable Long id) {

	        claimService.deleteClaim(id);
	        return ResponseEntity.noContent().build();
	    }
}
