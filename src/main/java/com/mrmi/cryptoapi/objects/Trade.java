package com.mrmi.cryptoapi.objects;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Trade {
    private Long id;
    private Long buyOrderId;
    private Long sellOrderId;
    private LocalDateTime createdDateTime;
    private double price;
    private double quantity;

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
