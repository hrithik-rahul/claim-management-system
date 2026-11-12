package claim_management_system.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RegisterRequest {
	 	@NotBlank
	    private String username;

	    @NotBlank
	    @Size(min = 8)
	    private String password;
	    @NotBlank
	    private String fullName;

	    private String phoneNumber;

	   

	    @NotBlank
	    @Email
	    private String email;
	    public RegisterRequest(
	            String username,
	            String password,
	            String fullName,
	            String email,
	            String phoneNumber) {

	        this.username = username;
	        this.password = password;
	        this.fullName = fullName;
	        this.email = email;
	        this.phoneNumber = phoneNumber;
	    }

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}
		public String getEmail() {
	        return email;
	    }

	    public void setEmail(String email) {
	        this.email = email;
	    }
	    public String getPhoneNumber() {
	        return phoneNumber;
	    }

	    public void setPhoneNumber(String phoneNumber) {
	        this.phoneNumber = phoneNumber;
	    }
	    public String getFullName() {
	        return fullName;
	    }

	    public void setFullName(String fullName) {
	        this.fullName = fullName;
	    }

		public RegisterRequest() {
			
		}
}
