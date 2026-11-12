package claim_management_system.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import claim_management_system.entity.User;

public interface UserRepo extends JpaRepository<User, Long> {
	Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);
    
    boolean existsByEmail(String email);
}
