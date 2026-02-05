package com.yachit.ecommercebackend.controller;

import com.yachit.ecommercebackend.entity.Order;
import com.yachit.ecommercebackend.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/{customerId}")
    public Order placeOrder(@PathVariable Long customerId) {
        return orderService.placeOrder(customerId);
    }
}
