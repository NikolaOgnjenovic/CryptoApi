package com.mrmi.cryptoapi.objects;

import java.time.LocalDateTime;

public class Trade {
    private final Long id;
    private final Long buyOrderId;
    private final Long sellOrderId;
    private final LocalDateTime createdDateTime;
    private final double price;
    private final double quantity;

    public Trade(Long id, Long buyOrderId, Long sellOrderId, LocalDateTime createdDateTime, double price, double quantity) {
        this.id = id;
        this.buyOrderId = buyOrderId;
        this.sellOrderId = sellOrderId;
        this.createdDateTime = createdDateTime;
        this.price = price;
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Trade{" +
                "id=" + id +
                ", buyOrderId=" + buyOrderId +
                ", sellOrderId=" + sellOrderId +
                ", createdDateTime=" + createdDateTime +
                ", price=" + price +
                ", quantity=" + quantity +
                '}';
    }
}
