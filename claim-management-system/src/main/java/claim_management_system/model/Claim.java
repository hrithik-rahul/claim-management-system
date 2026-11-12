package claim_management_system.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;



import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "claims")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Claim {
	   @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @Column(nullable = false)
	    private String claimType;

	    @Column(length = 1000)
	    private String description;

	    @Column(nullable = false)
	    private String status = "SUBMITTED";

	    private LocalDateTime submittedAt;

	    @ManyToOne(fetch = FetchType.LAZY, optional = false)
	    @JoinColumn(name = "customer_id", nullable = false)
	    @JsonIgnore
	    private Customer customer;
	    
	    @Column(nullable = false)
	    private boolean deleted = false;

	    public boolean isDeleted() {
			return deleted;
		}

		public void setDeleted(boolean deleted) {
			this.deleted = deleted;
		}

		@PrePersist
	    public void prePersist() {
	        if (submittedAt == null) {
	            submittedAt = LocalDateTime.now();
	        }
	    }
	    
	    public Long getId() {
	        return id;
	    }

	    public void setId(Long id) {
	        this.id = id;
	    }

	    public String getClaimType() {
	        return claimType;
	    }

	    public void setClaimType(String claimType) {
	        this.claimType = claimType;
	    }

	    public String getDescription() {
	        return description;
	    }

	    public void setDescription(String description) {
	        this.description = description;
	    }

	    public String getStatus() {
	        return status;
	    }

	    public void setStatus(String status) {
	        this.status = status;
	    }

	    public LocalDateTime getSubmittedAt() {
	        return submittedAt;
	    }

	    public void setSubmittedAt(LocalDateTime submittedAt) {
	        this.submittedAt = submittedAt;
	    }

	    public Customer getCustomer() {
	        return customer;
	    }

	    public void setCustomer(Customer customer) {
	        this.customer = customer;
	    }
}