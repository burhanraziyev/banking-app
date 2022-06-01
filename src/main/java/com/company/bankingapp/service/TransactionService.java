package com.company.bankingapp.service;

import com.company.bankingapp.model.entity.AccountEntity;
import com.company.bankingapp.model.entity.TransactionEntity;
import com.company.bankingapp.model.enums.CurrencyType;
import com.company.bankingapp.model.enums.TransactionType;

import java.math.BigDecimal;

public interface TransactionService {
    TransactionEntity createTransaction(AccountEntity accountEntity, BigDecimal amount,
                                        CurrencyType currencyType, TransactionType transactionType);
}
