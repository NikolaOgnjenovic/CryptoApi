package com.mrmi.cryptoapi;

import com.mrmi.cryptoapi.objects.Order;
import com.mrmi.cryptoapi.objects.OrderBook;
import com.mrmi.cryptoapi.services.OrderBookService;
import com.mrmi.cryptoapi.services.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class OrderServiceTest {
    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderBookService orderBookService;

    @Autowired
    private OrderBook orderBook;

    private void initialiseOrderBook() {
        List<Order> sellOrders = new ArrayList<>();
        sellOrders.add(new Order(0,
                LocalDateTime.now(),
                "SELL",
                9,
                100,
                0,
                "OPEN",
                new ArrayList<>())
        );
        sellOrders.add(new Order(1,
                LocalDateTime.now(),
                "SELL",
                10,
                50,
                0,
                "OPEN",
                new ArrayList<>()
        ));

        sellOrders.add(new Order(2,
                LocalDateTime.now(),
                "SELL",
                11,
                1000,
                0,
                "OPEN",
                new ArrayList<>()
        ));
        sellOrders.add(new Order(3,
                        LocalDateTime.now(),
                        "SELL",
                        15,
                        250,
                        0,
                        "OPEN",
                        new ArrayList<>()
                )
        );
        List<Order> buyOrders = new ArrayList<>();
        buyOrders.add(new Order(4,
                LocalDateTime.now(),
                "BUY",
                7,
                250,
                0,
                "OPEN",
                new ArrayList<>()
        ));
        buyOrders.add(new Order(5,
                LocalDateTime.now(),
                "BUY",
                6,
                800,
                0,
                "OPEN",
                new ArrayList<>()
        ));
        buyOrders.add(new Order(6,
                LocalDateTime.now(),
                "BUY",
                5,
                150,
                0,
                "OPEN",
                new ArrayList<>()
        ));
        buyOrders.add(new Order(7,
                LocalDateTime.now(),
                "BUY",
                1,
                5000,
                0,
                "OPEN",
                new ArrayList<>()
        ));

        orderBook.setSellOrders(sellOrders);
        orderBook.setBuyOrders(buyOrders);
    }

    private void processBuyOrder(double price, double quantity) {
        orderService.processOrder(
                new Order(8,
                        LocalDateTime.now(),
                        "BUY",
                        price,
                        quantity,
                        0,
                        "OPEN",
                        new ArrayList<>())
        );
    }

    private void processSellOrder(double price, double quantity) {
        orderService.processOrder(
                new Order(8,
                        LocalDateTime.now(),
                        "SELL",
                        price,
                        quantity,
                        0,
                        "OPEN",
                        new ArrayList<>())
        );
    }

    @Test
    public void buyOrderTest() {
        initialiseOrderBook();
        processBuyOrder(9, 50);

        List<Order> updatedSellOrders = orderBookService.getOpenSellOrders();

        assertTrue((updatedSellOrders).stream().anyMatch(order -> order.getPrice() == 9 && order.getQuantity() == 50));
        assertEquals(4, updatedSellOrders.size());
        assertEquals(4, orderBookService.getOpenBuyOrders().size());
    }

    @Test
    public void sellOrderTest() {
        initialiseOrderBook();

        processBuyOrder(9, 50);
        processSellOrder(7, 100);

        List<Order> updatedBuyOrders = orderBookService.getOpenBuyOrders();

        assertTrue((updatedBuyOrders).stream().anyMatch(order -> order.getPrice() == 7 && order.getQuantity() == 150));
        assertEquals(4, updatedBuyOrders.size());
        assertEquals(4, orderBookService.getOpenSellOrders().size());
    }

    @Test
    public void newBuyOrderTest() {
        initialiseOrderBook();

        processBuyOrder(9, 50);
        processSellOrder(7, 100);
        processBuyOrder(8, 70);

        List<Order> updatedBuyOrders = orderBookService.getOpenBuyOrders();

        assertTrue((updatedBuyOrders).stream().anyMatch(order -> order.getPrice() == 8 && order.getQuantity() == 70));
        assertEquals(5, updatedBuyOrders.size());
        assertEquals(4, orderBookService.getOpenSellOrders().size());
    }

    @Test
    public void closedBuyOrderTest() {
        initialiseOrderBook();

        // The third test in the pdf expects the state of the order book to be equal to the one after the second test
        processBuyOrder(9, 50);
        processSellOrder(7, 100);
        processBuyOrder(8, 70);
        processSellOrder(6, 200);

        List<Order> updatedBuyOrders = orderBookService.getOpenBuyOrders();

        assertTrue((updatedBuyOrders).stream().anyMatch(order -> order.getPrice() == 7 && order.getQuantity() == 20));
        assertTrue((updatedBuyOrders).stream().noneMatch(order -> order.getPrice() == 8 && order.getQuantity() == 70));
        assertEquals(4, updatedBuyOrders.size());
        assertEquals(4, orderBookService.getOpenSellOrders().size());
    }

    @Test
    public void closedSellOpenedBuyOrderTest() {
        initialiseOrderBook();

        processBuyOrder(9, 50);
        processSellOrder(7, 100);
        processBuyOrder(8, 70);
        processSellOrder(6, 200);
        processBuyOrder(9, 80);

        List<Order> updatedBuyOrders = orderBookService.getOpenBuyOrders();
        List<Order> updatedSellOrders = orderBookService.getOpenSellOrders();

        assertTrue((updatedBuyOrders).stream().anyMatch(order -> order.getPrice() == 9 && order.getQuantity() == 30));
        assertTrue((updatedSellOrders).stream().noneMatch(order -> order.getPrice() == 9 && order.getQuantity() == 50));
        assertEquals(5, updatedBuyOrders.size());
        assertEquals(3, orderBookService.getOpenSellOrders().size());
    }
}
