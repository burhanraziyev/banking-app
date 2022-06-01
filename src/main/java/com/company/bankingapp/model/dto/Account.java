package com.company.bankingapp.model.dto;

import com.company.bankingapp.model.enums.AccountStatus;
import com.company.bankingapp.model.enums.CurrencyType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

public record Account(
        Long id,
        String number,
        BigDecimal balance,
        AccountStatus status,
        CurrencyType currencyType,
        Long customerId,
        Set<Transaction> transactions,
        String createdBy,
        LocalDateTime createdDate,
        String lastModifiedBy,
        LocalDateTime lastModifiedDate) {
}
