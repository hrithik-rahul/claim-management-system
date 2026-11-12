package claim_management_system.service;

import java.util.List;

import claim_management_system.model.Customer;

public interface CustomerService {
	Customer saveCustomer(Customer customer);

    List<Customer> getAllCustomers();

    Customer getCustomerById(Long id);

    Customer updateCustomer(Long id, Customer customer);

    void deleteCustomer(Long id);
    

}
