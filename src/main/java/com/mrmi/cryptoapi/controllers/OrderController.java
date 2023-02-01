package com.mrmi.cryptoapi.controllers;

import com.mrmi.cryptoapi.objects.Order;
import com.mrmi.cryptoapi.services.OrderService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/order")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // POST localhost:8080/order?id=1&currencyPair=BTCUSD&type=BUY&price=2015.00&quantity=0.3
    @PostMapping()
    public Order processOrder(Order order) {
        return orderService.processOrder(order);
    }

    // GET localhost:8080/order/1
    @GetMapping(path="/{id}")
    public Order order(@PathVariable(name="id") int id) {
        return orderService.getOrderById(id);
    }

    // DELETE localhost:8080/all
    @DeleteMapping(path="/all")
    public void deleteAll() {
        orderService.deleteOrders();
    }
}
