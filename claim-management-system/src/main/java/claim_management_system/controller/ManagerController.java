package claim_management_system.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import claim_management_system.model.Claim;
import claim_management_system.service.ClaimService;

@RestController
@RequestMapping("/api/manager")
@CrossOrigin(origins = "http://localhost:4200")
public class ManagerController {

    private final ClaimService claimService;

    public ManagerController(ClaimService claimService) {
        this.claimService = claimService;
    }

    // Get all active claims
    @GetMapping("/claims")
    public ResponseEntity<List<Claim>> getAllClaims() {
        return ResponseEntity.ok(claimService.getAllClaims());
    }

    // Get claim by ID
    @GetMapping("/claims/{id}")
    public ResponseEntity<Claim> getClaimById(@PathVariable Long id) {
        return ResponseEntity.ok(claimService.getClaimById(id));
    }

    // Update claim status
    @PutMapping("/claims/status/{id}")
    public ResponseEntity<Claim> updateClaimStatus(
            @PathVariable Long id,
            @RequestBody Map<String, String> request) {

        Claim updatedClaim = claimService.updateClaimStatus(
                id, request.get("status"));

        return ResponseEntity.ok(updatedClaim);
    }

    // Delete claim (soft delete)
    @DeleteMapping("/claims/{id}")
    public ResponseEntity<Void> deleteClaim(@PathVariable Long id) {
        claimService.deleteClaim(id);
        return ResponseEntity.noContent().build();
    }
}