package com.yachit.ecommercebackend.service;

import com.yachit.ecommercebackend.entity.Order;

public interface OrderService {
    Order placeOrder(Long customerId);
}
