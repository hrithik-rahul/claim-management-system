package claim_management_system.dtos;

public class LoginResponse {
	 private String token;
	    private String tokenType;
	    private String fullName;
	    private String username;
	    private String role;

	    public LoginResponse(String token, String fullName, String username,String role) {
	        this.token = token;
	        this.tokenType = "Bearer";
	        this.fullName = fullName;
	        this.username = username;
	        this.role=role;
	    }

	    public String getToken() {
	        return token;
	    }

	    public String getTokenType() {
	        return tokenType;
	    }

	    public String getFullName() {
	        return fullName;
	    }
	    public String getUsername() {
	        return username;
	    }
	    public String getRole() {
	        return role;
	    }
}
