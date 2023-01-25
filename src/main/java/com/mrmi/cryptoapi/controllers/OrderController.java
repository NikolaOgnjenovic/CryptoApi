package com.mrmi.cryptoapi.controllers;

import com.mrmi.cryptoapi.objects.Order;
import com.mrmi.cryptoapi.services.OrderService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping(path="/order")
    public Order processOrder(Order order) {
        return orderService.processOrder(order);
    }
}
