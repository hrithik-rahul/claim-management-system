package claim_management_system.service;

import java.util.List;

import claim_management_system.model.Claim;

public interface ClaimService {
	// Customer operations
   public  Claim createClaim(Long customerId, String claimType, String description);

   public List<Claim> getClaimsByCustomerId(Long customerId);

   public  Claim updateClaim(Long id,String claimType, String description);

//   public   void deleteCustomerClaim(Long id, Long customerId);
//Manager operations
   public List<Claim> getAllClaims();

   public Claim getClaimById(Long id);

   public Claim updateClaimStatus(Long id, String status);

   public void deleteClaim(Long id);
}
