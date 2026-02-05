package com.yachit.ecommercebackend.service.impl;

import com.yachit.ecommercebackend.entity.Cart;
import com.yachit.ecommercebackend.entity.CartItem;
import com.yachit.ecommercebackend.repository.CartRepository;
import com.yachit.ecommercebackend.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;

    @Override
    public Cart getCartByCustomerId(Long customerId) {
        return cartRepository.findByCustomerCustomerId(customerId)
                .orElseThrow(() -> new RuntimeException("Cart not found for customer " + customerId));
    }

    @Override
    public Cart addItemToCart(Long customerId, String productName, int quantity, double price) {
        Cart cart = getCartByCustomerId(customerId);

        CartItem item = CartItem.builder()
                .cart(cart)
                .productName(productName)
                .quantity(quantity)
                .priceAtTime(BigDecimal.valueOf(price))
                .build();

        cart.getItems().add(item);
        return cartRepository.save(cart);
    }
}
