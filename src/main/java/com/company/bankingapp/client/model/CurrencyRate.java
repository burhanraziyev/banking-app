package com.company.bankingapp.client.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CurrencyRate(
        LocalDateTime date,
        String historical,
        Info info,
        Query query,
        BigDecimal result,
        Boolean success
) {
}
