
package com.kodewala.ecommerce.service;

import java.sql.SQLException;

import com.kodewala.ecommerce.model.Customer;
import com.kodewala.ecommerce.repository.CustomerRepository;

public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer getCustomerById(int customerId) throws SQLException {

        if (customerId <= 0) {
            throw new IllegalArgumentException(
                    "Customer ID must be greater than zero");
        }

        Customer customer = customerRepository.findById(customerId);

        if (customer == null) {
            throw new IllegalArgumentException(
                    "Customer not found with ID: " + customerId);
        }

        return customer;
    }
}
