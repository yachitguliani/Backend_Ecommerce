package com.yachit.ecommercebackend.controller;

import com.yachit.ecommercebackend.entity.Cart;
import com.yachit.ecommercebackend.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/carts")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    // View cart
    @GetMapping("/{customerId}")
    public Cart viewCart(@PathVariable Long customerId) {
        return cartService.getCartByCustomerId(customerId);
    }

    // Add item to cart
    @PostMapping("/{customerId}/items")
    public Cart addItem(
            @PathVariable Long customerId,
            @RequestParam String productName,
            @RequestParam int quantity,
            @RequestParam double price
    ) {
        return cartService.addItemToCart(customerId, productName, quantity, price);
    }
}
