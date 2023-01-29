package com.mrmi.cryptoapi.services;

import com.mrmi.cryptoapi.objects.Order;
import com.mrmi.cryptoapi.objects.OrderBook;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderBookService {
    private final OrderBook orderBook;

    public OrderBookService(OrderBook orderBook) {
        this.orderBook = orderBook;
    }

    public OrderBook getOrderBook() {
        return orderBook;
    }

    public List<Order> getSellOrders() {
        return orderBook.getSellOrders();
    }

    public List<Order> getBuyOrders() {
        return orderBook.getBuyOrders();
    }

    public void addBuyOrder(Order order) {
        orderBook.addBuyOrder(order);
    }

    public void addSellOrder(Order order) {
        orderBook.addSellOrder(order);
    }

    public void deleteOrders() {
        orderBook.deleteOrders();
    }

    public Order getOrder(int id) {
        try {
            return getBuyOrders().get(id);
        } catch (Exception e) {
            return getSellOrders().get(id);
        }
    }
}
