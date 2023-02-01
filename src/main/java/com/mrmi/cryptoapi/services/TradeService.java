package com.mrmi.cryptoapi.services;

import com.mrmi.cryptoapi.objects.Trade;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class TradeService {
    private Long tradeId = 0L;

    // TODO: Save trade list in a database
    private final List<Trade> tradeList = new ArrayList<>(); // TODO: save in database
    public Trade createTrade(Long buyOrderId, Long sellOrderId, double price, double quantity) {
        tradeId += 1;
        Trade trade = new Trade(tradeId, buyOrderId, sellOrderId, LocalDateTime.now(), price, quantity);
        tradeList.add(trade);
        System.out.println("Created trade: " + trade);

        return trade;
    }

    public void deleteTrades() {
        tradeList.clear();
    }
}
