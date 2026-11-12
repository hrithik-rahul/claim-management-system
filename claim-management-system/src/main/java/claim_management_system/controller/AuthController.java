package claim_management_system.controller;





import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import claim_management_system.dtos.LoginRequest;
import claim_management_system.dtos.LoginResponse;
import claim_management_system.dtos.RegisterRequest;
import claim_management_system.entity.User;
import claim_management_system.repository.UserRepo;
import claim_management_system.service.AuthService;
import jakarta.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/auth")
public class AuthController {
	 private final AuthService authService;
	 private final UserRepo userRepo;
	 private static final Logger logger =
		        LoggerFactory.getLogger(AuthController.class);

	    public AuthController(AuthService authService,UserRepo userRepo) {
	        this.authService = authService;
	        this.userRepo=userRepo;
	    }

	    @PostMapping("/register")
	    public ResponseEntity<String> register(
	            @Valid @RequestBody RegisterRequest request) {

	        try {
	            String result = authService.register(request);

	            return ResponseEntity
	                    .status(HttpStatus.CREATED)
	                    .body(result);

	        } catch (IllegalArgumentException ex) {
	            return ResponseEntity
	                    .status(HttpStatus.CONFLICT)
	                    .body(ex.getMessage());
	        }
	    }
//	    @PostMapping("/login")
//	    public ResponseEntity<String> login(
//	            @Valid @RequestBody RegisterRequest request) {
//
//	        try {
//	            String result = authService.login(
//	                    request.getUsername(),
//	                    request.getPassword()
//	            );
//
//	            return ResponseEntity.ok(result);
//
//	        } catch (IllegalArgumentException ex) {
//	            return ResponseEntity
//	                    .status(HttpStatus.UNAUTHORIZED)
//	                    .body(ex.getMessage());
//	        }
//	    }
//	    
	    @PostMapping("/login")
	    public ResponseEntity<?> login(
	            @Valid @RequestBody LoginRequest request) {

	        try {
	            LoginResponse response = authService.login(
	                    request.getUsername(),
	                    request.getPassword()
	            );

	            return ResponseEntity.ok(response);

	        } catch (IllegalArgumentException ex) {
	            return ResponseEntity
	                    .status(HttpStatus.UNAUTHORIZED)
	                    .body(ex.getMessage());
	        }
	    }
}
