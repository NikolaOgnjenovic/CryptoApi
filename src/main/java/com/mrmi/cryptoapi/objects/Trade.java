package com.mrmi.cryptoapi.objects;

import java.time.LocalDateTime;

// By default, records come with getters and constructors. No setters - they're final.
public record Trade(Long id,
                    Long buyOrderId,
                    Long sellOrderId,
                    LocalDateTime createdDateTime,
                    double price,
                    double quantity) {
}
