package com.yachit.ecommercebackend.service;

import com.yachit.ecommercebackend.entity.Customer;

public interface CustomerService {
    Customer createCustomer(Customer customer);
    Customer getCustomerById(Long id);
}
