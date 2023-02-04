package com.mrmi.cryptoapi.objects;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Component
public class OrderBook {
    private List<Order> buyOrders = new ArrayList<>();
    private List<Order> sellOrders = new ArrayList<>();

    // TODO: Store in database. Sorting when getting is a slow & temporary solution.
    public List<Order> getBuyOrders() {
        buyOrders.sort(Comparator.comparing(Order::getPrice, Comparator.reverseOrder()).thenComparing(Order::getCreatedDateTime));
        return buyOrders;
    }

    public List<Order> getSellOrders() {
        sellOrders.sort(Comparator.comparing(Order::getPrice).thenComparing(Order::getCreatedDateTime));
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

    public void setBuyOrders(List<Order> orders) {
        this.buyOrders = orders;
    }

    public void setSellOrders(List<Order> orders) {
        this.sellOrders = orders;
    }
}
