package claim_management_system.service;



import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import claim_management_system.dtos.LoginResponse;
import claim_management_system.dtos.RegisterRequest;
import claim_management_system.entity.User;
import claim_management_system.model.Customer;
import claim_management_system.repository.CustomerRepo;
import claim_management_system.repository.UserRepo;
import jakarta.transaction.Transactional;

@Service
public class AuthServiceImpl implements AuthService {
	 private final UserRepo userRepository;
	    private final PasswordEncoder passwordEncoder;
	    private final JwtService jwtService;
	    private final CustomerRepo customerRepo;

	    public AuthServiceImpl(UserRepo userRepository,
	    		CustomerRepo customerRepo,
	                           PasswordEncoder passwordEncoder,
	                           JwtService jwtService) {
	    	
	        this.userRepository = userRepository;
	        this.passwordEncoder = passwordEncoder;
	        this.customerRepo = customerRepo;
	        this.jwtService = jwtService;
	    }

	    @Override
	    @Transactional
	    public String register(RegisterRequest request) {

	    	 if (userRepository.existsByUsername(request.getUsername())) {
	    	        throw new IllegalArgumentException("Username already exists");
	    	    }

	    	    if (userRepository.existsByEmail(request.getEmail())) {
	    	        throw new IllegalArgumentException("Email already exists");
	    	    }
	    	    // Create the customer record
	    	    Customer customer = new Customer();
	    	    customer.setName(request.getFullName());
	    	    customer.setEmail(request.getEmail());
	    	    customer.setPhone(request.getPhoneNumber());

	    	    customerRepo.save(customer);

	    	    User user = new User();
	    	    user.setUsername(request.getUsername());
	    	    user.setPassword(passwordEncoder.encode(request.getPassword()));
	    	    user.setFullName(request.getFullName());
	    	    user.setEmail(request.getEmail());
	    	    user.setPhoneNumber(request.getPhoneNumber());
	    	    user.setRole("ROLE_USER");
	    	    user.setCustomer(customer);

	    	    userRepository.save(user);

	    	    return "User registered successfully";
	    }
	    @Override
	    public LoginResponse login(String username, String password) {
	        User user = userRepository.findByUsername(username)
	                .orElseThrow(() -> new IllegalArgumentException(
	                        "Invalid username or password"));
	       
	        
	        if (!passwordEncoder.matches(password, user.getPassword())) {
	            throw new IllegalArgumentException(
	                    "Invalid username or password");
	        }

	        String token = jwtService.generateToken(
	                user.getUsername(), user.getRole());

	        return new LoginResponse(
	                token,
	                user.getFullName(),
	                user.getUsername(),
	                user.getRole()
	        );
	    }
}
