package com.yachit.ecommercebackend.repository;

import com.yachit.ecommercebackend.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}
