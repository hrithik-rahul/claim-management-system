package claim_management_system.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import claim_management_system.entity.User;
import claim_management_system.repository.UserRepo;

@Service
public class CustomUserDetailsService implements UserDetailsService{
	  private final UserRepo userRepo;

	    public CustomUserDetailsService(UserRepo userRepo) {
	        this.userRepo = userRepo;
	    }

	    @Override
	    public UserDetails loadUserByUsername(String username)
	            throws UsernameNotFoundException {

	        User user = userRepo.findByUsername(username)
	                .orElseThrow(() ->
	                        new UsernameNotFoundException("User not found"));

	        return org.springframework.security.core.userdetails.User
	                .withUsername(user.getUsername())
	                .password(user.getPassword())
	                .authorities(user.getRole())
	                .build();
	    }
}
