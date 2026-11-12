package claim_management_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import claim_management_system.model.Customer;

public interface CustomerRepo extends JpaRepository<Customer, Long> {

}
