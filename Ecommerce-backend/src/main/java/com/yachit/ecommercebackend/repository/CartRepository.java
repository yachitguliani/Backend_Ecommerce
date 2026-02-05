package com.yachit.ecommercebackend.repository;

import com.yachit.ecommercebackend.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByCustomerCustomerId(Long customerId);


    }
