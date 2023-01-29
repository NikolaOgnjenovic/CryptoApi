package com.mrmi.cryptoapi.objects;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderBook {
    private final List<Order> buyOrders = new ArrayList<>();
    private final List<Order> sellOrders = new ArrayList<>();

    public List<Order> getBuyOrders() {
        return buyOrders;
    }

    public List<Order> getSellOrders() {
        return sellOrders;
    }

    public void addBuyOrder(Order order) {
        buyOrders.add(order);
    }

    public void addSellOrder(Order order) {
        sellOrders.add(order);
    }

    public void deleteOrders() {
        buyOrders.clear();
        sellOrders.clear();
    }
}
