package claim_management_system.event;

import java.time.LocalDateTime;





public class ClaimCreatedEvent {
	   private Long claimId;
	    private String claimType;
	    private String description;
	    private String status;
	    private LocalDateTime submittedAt;
	    
	 public ClaimCreatedEvent() {
			
		}
	 public ClaimCreatedEvent(Long claimId, String claimType, String description, String status,
			LocalDateTime submittedAt, Long customerId, String customerName, String customerEmail,
			String customerPhone) {
		super();
		this.claimId = claimId;
		this.claimType = claimType;
		this.description = description;
		this.status = status;
		this.submittedAt = submittedAt;
		this.customerId = customerId;
		this.customerName = customerName;
		this.customerEmail = customerEmail;
		this.customerPhone = customerPhone;
	}
	 public Long getClaimId() {
			return claimId;
		}
		public void setClaimId(Long claimId) {
			this.claimId = claimId;
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
		public Long getCustomerId() {
			return customerId;
		}
		public void setCustomerId(Long customerId) {
			this.customerId = customerId;
		}
		public String getCustomerName() {
			return customerName;
		}
		public void setCustomerName(String customerName) {
			this.customerName = customerName;
		}
		public String getCustomerEmail() {
			return customerEmail;
		}
		public void setCustomerEmail(String customerEmail) {
			this.customerEmail = customerEmail;
		}
		public String getCustomerPhone() {
			return customerPhone;
		}
		public void setCustomerPhone(String customerPhone) {
			this.customerPhone = customerPhone;
		}
		// Customer details
	    private Long customerId;
	    private String customerName;
	    private String customerEmail;
	    private String customerPhone;
}
