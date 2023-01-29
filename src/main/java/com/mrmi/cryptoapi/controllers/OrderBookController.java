package com.mrmi.cryptoapi.controllers;

import com.mrmi.cryptoapi.objects.OrderBook;
import com.mrmi.cryptoapi.services.OrderBookService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderBookController {
    private final OrderBookService orderBookService;

    public OrderBookController(OrderBookService orderBookService) {
        this.orderBookService = orderBookService;
    }

    @GetMapping(path="/orderbook")
    public OrderBook getOrderBook() {
        return orderBookService.getOrderBook();
    }
}
