package com.mrmi.cryptoapi.objects;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Order {
    public long id;
    private final String currencyPair = "BTCUSD";
    private LocalDateTime createdDateTime;
    private String type; // BUY or SELL
    private double price;
    private double quantity;
    private double filledQuantity;
    private String status; // OPEN or CLOSED
    private List<Trade> trades;

    public Order(long id, LocalDateTime createdDateTime, String type, double price, double quantity, double filledQuantity, String status, List<Trade> trades) {
        this.id = id;
        this.createdDateTime = createdDateTime;
        this.type = type;
        this.price = price;
        this.quantity = quantity;
        this.filledQuantity = filledQuantity;
        this.status = status;
        this.trades = trades;
    }

    public long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCurrencyPair() {
        return currencyPair;
    }

    public LocalDateTime getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(LocalDateTime createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        // Round the price to 2 decimal points
        this.price = Math.round(price * 100.0) / 100.0;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public double getFilledQuantity() {
        return filledQuantity;
    }

    public void setFilledQuantity(double filledQuantity) {
        this.filledQuantity = filledQuantity;
    }

    public void increaseFilledQuantity(double filledQuantity) {
        this.filledQuantity += filledQuantity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Trade> getTrades() {
        return trades;
    }

    public void setTrades(List<Trade> trades) {
        this.trades = trades;
    }

    public void addTrade(Trade trade) {
        this.trades.add(trade);
    }
}
