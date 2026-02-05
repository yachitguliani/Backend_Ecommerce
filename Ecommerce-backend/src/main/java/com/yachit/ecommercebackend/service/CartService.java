package com.yachit.ecommercebackend.service;

import com.yachit.ecommercebackend.entity.Cart;

public interface CartService {
    Cart getCartByCustomerId(Long customerId);
    Cart addItemToCart(Long customerId, String productName, int quantity, double price);
}
