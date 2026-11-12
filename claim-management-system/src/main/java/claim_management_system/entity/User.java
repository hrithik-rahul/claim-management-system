package claim_management_system.entity;




import claim_management_system.model.Customer;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.Data;


@Entity
@Table(name = "app_users")
public class User {
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
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

		public String getRole() {
			return role;
		}

		public void setRole(String role) {
			this.role = role;
		}
		 public String getFullName() {
		        return fullName;
		    }

		    public void setFullName(String fullName) {
		        this.fullName = fullName;
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
		    public Customer getCustomer() {
		        return customer;
		    }

		    public void setCustomer(Customer customer) {
		        this.customer = customer;
		    }
		    
		    @OneToOne
		    @JoinColumn(name = "customer_id", unique = true)
		    private Customer customer;
		    
		    @Column
		    private String phoneNumber;
		    
		@Column(nullable = false, unique = true)
	    private String username;

	    @Column(nullable = false)
	    private String password;

	    @Column(nullable = false)
	    private String role;
	    
	    @Column(nullable = false)
	    private String fullName;

	    @Column(nullable = false, unique = true)
	    private String email;
	    
	
}
