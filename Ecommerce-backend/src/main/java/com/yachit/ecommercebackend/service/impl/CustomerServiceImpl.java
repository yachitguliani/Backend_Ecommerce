package com.yachit.ecommercebackend.service.impl;

import com.yachit.ecommercebackend.entity.Customer;
import com.yachit.ecommercebackend.repository.CustomerRepository;
import com.yachit.ecommercebackend.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.yachit.ecommercebackend.entity.Cart;


@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @Override
    public Customer createCustomer(Customer customer) {
        Cart cart = Cart.builder()
                .customer(customer)
                .status("ACTIVE")
                .build();
        customer.setCart(cart);

        return customerRepository.save(customer);
    }

    @Override
    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found: " + id));
    }
}
