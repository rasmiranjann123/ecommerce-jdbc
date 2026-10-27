
package com.kodewala.ecommerce.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import com.kodewala.ecommerce.repository.CustomerRepository;

class CustomerServiceTest {

    private final CustomerService customerService =
            new CustomerService(new CustomerRepository());

    @Test
    void shouldRejectInvalidCustomerId() {
        assertThrows(
                IllegalArgumentException.class,
                () -> customerService.getCustomerById(0)
        );
    }

    @Test
    void shouldRejectNegativeCustomerId() {
        assertThrows(
                IllegalArgumentException.class,
                () -> customerService.getCustomerById(-1)
        );
    }

    @Test
    void shouldRejectMissingCustomer() {
        assertThrows(
                IllegalArgumentException.class,
                () -> customerService.getCustomerById(99999)
        );
    }
}

