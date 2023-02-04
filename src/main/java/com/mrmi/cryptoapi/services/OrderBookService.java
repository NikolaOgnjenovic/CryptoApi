package com.mrmi.cryptoapi.services;

import com.mrmi.cryptoapi.exceptions.OrderNotFoundException;
import com.mrmi.cryptoapi.objects.Order;
import com.mrmi.cryptoapi.objects.OrderBook;
import org.springframework.stereotype.Service;

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

    public List<Order> getOpenSellOrders() {
        return orderBook.getSellOrders()
                .stream()
                .filter(o -> o.getStatus().equals("OPEN"))
                .toList();
    }

    public List<Order> getBuyOrders() {
        return orderBook.getBuyOrders();
    }

    public List<Order> getOpenBuyOrders() {
        return orderBook.getBuyOrders()
                .stream()
                .filter(o -> o.getStatus().equals("OPEN"))
                .toList();
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

    public Order getOrderById(int id) {
        for (Order o : getBuyOrders()) {
            if (o.getId() == id) {
                return o;
            }
        }
        for (Order o : getSellOrders()) {
            if (o.getId() == id) {
                return o;
            }
        }

        throw new OrderNotFoundException("Order with id "  + id + " does not exist.");
    }
}
