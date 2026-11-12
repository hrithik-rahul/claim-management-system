package claim_management_system.service;

public interface JwtService {
	   String generateToken(String username, String role);

	    String extractUsername(String token);

	    boolean isTokenValid(String token, String username);
}
