package com.mrmi.cryptoapi.services;

import com.mrmi.cryptoapi.objects.Order;
import com.mrmi.cryptoapi.objects.Trade;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Service
public class OrderService {
    private final OrderBookService orderBookService;
    private final TradeService tradeService;

    public OrderService(OrderBookService orderBookService, TradeService tradeService) {
        this.orderBookService = orderBookService;
        this.tradeService = tradeService;
    }

    public Order processOrder(Order order) {
        validateOrder(order);

        boolean buying = order.getType().equals("BUY");

        List<Order> givenOrders;
        if (buying) {
            // Get all OPEN SELL orders
            givenOrders = orderBookService.getSellOrders();
        } else {
            // Get all OPEN BUY orders
            givenOrders = orderBookService.getBuyOrders();
        }

        List<Order> openOrders = new ArrayList<>();
        for (Order o : givenOrders){
            if (o.getStatus().equals("OPEN")) {
                openOrders.add(o);
            }
        }

        List<Trade> orderTradeList = new ArrayList<>();
        double orderQuantity = order.getQuantity();
        order.setFilledQuantity(0);

        if (openOrders.size() > 0) {
            // Sort available orders by price
            // TODO: Store them sorted by price in the database
            openOrders.sort(Comparator.comparing(Order::getPrice));

            /*
            Untill all orders are bought:
            1) go through each open sell order
            2) buy it if the open sell order's price <= the buy order's price
            2) if the open sell order's price is > the buy order's price break
            */
            int openOrderIndex = 0;
            double givenOrderPrice = order.getPrice();
            double openOrderPrice = openOrders.get(openOrderIndex).getPrice();

            while (orderQuantity > 0) {
                if (openOrderPrice < givenOrderPrice) {
                    double openOrderQuantity = openOrders.get(openOrderIndex).getQuantity();

                    // If 6 sell orders are open and 10 are being bought, buy 10 - 6 = 4 will be purchased
                    double purchasedAmount = orderQuantity;
                    if (orderQuantity > openOrderQuantity) {
                        purchasedAmount -= openOrderQuantity;
                    }

                    // Decrease the quantity of buy orders
                    orderQuantity -= purchasedAmount;

                    // Increase the filled (realised) quantity of the given buy order
                    order.increaseFilledQuantity(purchasedAmount);

                    // Add the trade to the list
                    orderTradeList.add(tradeService.createTrade(
                            openOrders.get(openOrderIndex).getId(),
                            order.getId(),
                            purchasedAmount * openOrderPrice,
                            purchasedAmount)
                    );

                    // Close the current open sell order, iterate to the next open sell order, update the sell price
                    openOrders.get(openOrderIndex).setStatus("CLOSED");
                    openOrderIndex += 1;
                    openOrderPrice = openOrders.get(openOrderIndex).getPrice();
                } else {
                    break;
                }
            }
        }

        // Fill in the remanining fields for the order
        order.setTrades(orderTradeList);
        order.setQuantity(orderQuantity);

        // If all purchases haven't been made, mark the buy order as open
        if (orderQuantity > 0) {
            order.setStatus("OPEN");
        } else {
            order.setStatus("CLOSED");
        }
        order.setCreatedDateTime(LocalDateTime.now());

        if (buying) {
            orderBookService.addBuyOrder(order);
        } else {
            orderBookService.addSellOrder(order);
        }

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

    public void deleteOrders() {
        orderBookService.deleteOrders();
        tradeService.deleteTrades();
    }

    public Order getOrder(int id) {
        return orderBookService.getOrder(id);
    }
}
