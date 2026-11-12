package claim_management_system.controller;

import java.util.List;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import claim_management_system.model.Customer;
import claim_management_system.service.CustomerService;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {
	   private final CustomerService customerService;

	    public CustomerController(CustomerService customerService) {
	        this.customerService = customerService;
	    }

	    // Create customer
	    @PostMapping("/createCustomer")
	    public ResponseEntity<Customer> createCustomer( @RequestBody Customer customer) {

	        Customer savedCustomer =
	                customerService.saveCustomer(customer);

	        return ResponseEntity.status(HttpStatus.CREATED)
	                .body(savedCustomer);
	    }

	    // Get all customers
	    @GetMapping("/fetchCustomers")
	    public ResponseEntity<List<Customer>> getAllCustomers() {

	        return ResponseEntity.ok(
	                customerService.getAllCustomers());
	    }

	    // Get customer by ID
	    @GetMapping("/getById/{id}")
	    public ResponseEntity<Customer> getCustomerById(
	            @PathVariable Long id) {

	        return ResponseEntity.ok(
	                customerService.getCustomerById(id));
	    }

	    // Update customer
	    @PutMapping("/updateCustomer/{id}")
	    public ResponseEntity<Customer> updateCustomer(
	            @PathVariable Long id,
	            @RequestBody Customer customer) {

	        return ResponseEntity.ok(
	                customerService.updateCustomer(id, customer));
	    }

	    // Delete customer
	    @DeleteMapping("/deleteCustomer/{id}")
	    public ResponseEntity<Void> deleteCustomer(
	            @PathVariable Long id) {

	        customerService.deleteCustomer(id);

	        return ResponseEntity.noContent().build();
	    }
}
