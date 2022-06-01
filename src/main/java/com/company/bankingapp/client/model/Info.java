package com.company.bankingapp.client.model;

import java.math.BigDecimal;

public record Info(
        BigDecimal rate,
        Long timestamp
) {
}


