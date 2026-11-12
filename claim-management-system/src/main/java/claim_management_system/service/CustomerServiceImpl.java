package claim_management_system.service;

import java.util.List;

import org.springframework.stereotype.Service;

import claim_management_system.model.Customer;
import claim_management_system.repository.CustomerRepo;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepo customerRepository;

    public CustomerServiceImpl(CustomerRepo customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() ->
                    new RuntimeException("Customer not found with ID: " + id));
    }

    @Override
    public Customer updateCustomer(Long id, Customer customer) {
        Customer existingCustomer = getCustomerById(id);
 
        existingCustomer.setName(customer.getName());
        existingCustomer.setEmail(customer.getEmail());
        existingCustomer.setPhone(customer.getPhone());

        return customerRepository.save(existingCustomer);
    }

    @Override
    public void deleteCustomer(Long id) {
        Customer existingCustomer = getCustomerById(id);
        customerRepository.delete(existingCustomer);
    }
}
