package com.mrmi.cryptoapi.services;

import com.mrmi.cryptoapi.objects.Order;
import com.mrmi.cryptoapi.objects.OrderBook;
import com.mrmi.cryptoapi.objects.Trade;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Service
public class OrderService {
    private final OrderBook orderBook;
    private final TradeService tradeService;
    public OrderService(OrderBook orderBook, TradeService tradeService) {
        this.orderBook = orderBook;
        this.tradeService = tradeService;
    }

    public Order processOrder(Order order) {
        validateOrder(order);

        if (order.getType().equals("BUY")) {
            return processBuyOrder(order);
        } else {
            return processSellOrder(order);
        }
    }

    private Order processBuyOrder(Order order) {
        // Get all OPEN SELL orders
        List<Order> sellOrders = orderBook.getSellOrders();
        List<Order> openSellOrders = new ArrayList<>();
        for (Order o : sellOrders){
            if (o.getStatus().equals("OPEN")) {
                openSellOrders.add(o);
            }
        }

        List<Trade> orderTradeList = new ArrayList<>();
        double buyOrderQuantity = order.getQuantity();
        order.setFilledQuantity(0);

        if (openSellOrders.size() > 0) {
            // Sort available orders by price
            // TODO: Store them sorted by price in the database
            openSellOrders.sort(Comparator.comparing(Order::getPrice));

            /*
            Untill all orders are bought:
            1) go through each open sell order
            2) buy it if the open sell order's price <= the buy order's price
            2) if the open sell order's price is > the buy order's price break
            */
            int openSellOrderIndex = 0;
            double buyOrderPrice = order.getPrice();
            double sellOrderPrice = openSellOrders.get(openSellOrderIndex).getPrice();

            while (buyOrderQuantity > 0) {
                if (sellOrderPrice < buyOrderPrice) {
                    double sellOrderQuantity = openSellOrders.get(openSellOrderIndex).getQuantity();

                    // If 6 sell orders are open and 10 are being bought, buy 10 - 6 = 4 will be purchased
                    double purchasedAmount = buyOrderQuantity;
                    if (buyOrderQuantity > sellOrderQuantity) {
                        purchasedAmount -= sellOrderQuantity;
                    }

                    // Decrease the quantity of buy orders
                    buyOrderQuantity -= purchasedAmount;

                    // Increase the filled (realised) quantity of the given buy order
                    order.increaseFilledQuantity(purchasedAmount);

                    // Add the trade to the list
                    orderTradeList.add(tradeService.createTrade(
                            openSellOrders.get(openSellOrderIndex).getId(),
                            order.getId(),
                            purchasedAmount * sellOrderPrice,
                            purchasedAmount)
                    );

                    // Close the current open sell order, iterate to the next open sell order, update the sell price
                    openSellOrders.get(openSellOrderIndex).setStatus("CLOSED");
                    openSellOrderIndex += 1;
                    sellOrderPrice = openSellOrders.get(openSellOrderIndex).getPrice();
                } else {
                    break;
                }
            }
        }

        // Fill in the remanining fields for the order
        order.setTrades(orderTradeList);
        order.setQuantity(buyOrderQuantity);
        // If all purchases haven't been made, mark the buy order as open
        if (buyOrderQuantity > 0) {
            order.setStatus("OPEN");
        } else {
            order.setStatus("CLOSED");
        }
        order.setCreatedDateTime(LocalDateTime.now());

        orderBook.addBuyOrder(order);

        return order;
    }

    private Order processSellOrder(Order order) {
        // TODO: process sell orders similarly to buy orders in order to test out both types of orders
        return order;
    }

    private void validateOrder(Order order) {
        if(!order.getCurrencyPair().equals("BTCUSD")) {
            throw new IllegalStateException("An order cannot have " + order.getCurrencyPair() + " as a currency pair.");
        }
        if (Objects.isNull(order.getType()) || !(order.getType().equals("BUY") || order.getType().equals("SELL"))) {
            throw new IllegalStateException("The order type " + order.getType() + " is not valid.");
        }
        if (order.getPrice() < 0) {
            throw new IllegalStateException("An order cannot have the negative price " + order.getPrice());
        }
        if (order.getQuantity() < 0) {
            throw new IllegalStateException("An order cannot have a negative quantity of " + order.getQuantity());
        }
    }
}
