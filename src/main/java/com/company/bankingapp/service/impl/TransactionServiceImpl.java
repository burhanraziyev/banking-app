package com.company.bankingapp.service.impl;

import com.company.bankingapp.model.dto.Transaction;
import com.company.bankingapp.model.entity.AccountEntity;
import com.company.bankingapp.model.entity.TransactionEntity;
import com.company.bankingapp.model.enums.CurrencyType;
import com.company.bankingapp.model.enums.TransactionType;
import com.company.bankingapp.repository.TransactionRepository;
import com.company.bankingapp.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;

    @Override
    public TransactionEntity createTransaction(AccountEntity accountEntity, BigDecimal amount,
                                               CurrencyType currencyType, TransactionType transactionType) {
        return TransactionEntity.builder()
                .accountEntity(accountEntity)
                .amount(amount)
                .currencyType(currencyType)
                .transactionType(transactionType)
                .build();
    }
}
