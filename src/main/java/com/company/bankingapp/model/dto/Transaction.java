package com.company.bankingapp.model.dto;

import com.company.bankingapp.model.enums.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record Transaction(
        Long id,
        BigDecimal amount,
        Long accountId,
        TransactionType transactionType,
        String createdBy,
        LocalDateTime createdDate,
        String lastModifiedBy,
        LocalDateTime lastModifiedDate) {
}
