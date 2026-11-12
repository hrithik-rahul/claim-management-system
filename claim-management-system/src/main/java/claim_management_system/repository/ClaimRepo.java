package claim_management_system.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import claim_management_system.model.Claim;

public interface ClaimRepo extends JpaRepository<Claim, Long> {
	List<Claim> findByCustomer_Id(Long customerId);
	
	@Query("SELECT c FROM Claim c WHERE c.deleted = false")
	List<Claim> findActiveClaims();
	
	@Query("SELECT c FROM Claim c WHERE c.customer.id = :customerId AND c.deleted = false")
	List<Claim> findActiveClaimsByCustomerId(
	        @Param("customerId") Long customerId);
}
