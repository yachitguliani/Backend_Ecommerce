package com.yachit.ecommercebackend.service.impl;

import com.yachit.ecommercebackend.entity.*;
import com.yachit.ecommercebackend.repository.CartRepository;
import com.yachit.ecommercebackend.repository.OrderRepository;
import com.yachit.ecommercebackend.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;

    @Override
    public Order placeOrder(Long customerId) {
        Cart cart = cartRepository.findByCustomerCustomerId(customerId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        if (cart.getItems().isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        Order order = Order.builder()
                .customer(cart.getCustomer())
                .build();

        BigDecimal total = BigDecimal.ZERO;

        for (CartItem cartItem : cart.getItems()) {
            OrderItem orderItem = OrderItem.builder()
                    .order(order)
                    .productName(cartItem.getProductName())
                    .quantity(cartItem.getQuantity())
                    .priceAtTime(cartItem.getPriceAtTime())
                    .build();

            order.getItems().add(orderItem);

            total = total.add(
                    cartItem.getPriceAtTime()
                            .multiply(BigDecimal.valueOf(cartItem.getQuantity()))
            );
        }

        order.setTotalAmount(total);

        // clear cart
        cart.getItems().clear();
        cart.setStatus("CHECKED_OUT");

        return orderRepository.save(order);
    }
}
