package claim_management_system.service;

import claim_management_system.dtos.LoginResponse;
import claim_management_system.dtos.RegisterRequest;

public interface AuthService {
	public String register(RegisterRequest request);
	 public LoginResponse login(String username, String password) ;
}
