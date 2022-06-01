package com.company.bankingapp.mapper;

import com.company.bankingapp.model.dto.Transaction;
import com.company.bankingapp.model.entity.TransactionEntity;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class TransactionMapper {

    public Transaction toTransaction(TransactionEntity from) {
        return new Transaction(
                from.getId(),
                from.getAmount(),
                from.getAccountEntity().getId(),
                from.getTransactionType(),
                from.getCreatedBy(),
                from.getCreatedDate(),
                from.getLastModifiedBy(),
                from.getLastModifiedDate()
        );
    }

    public Set<Transaction> toTransactionSet(Set<TransactionEntity> transactionEntities) {
        return transactionEntities.stream().map(this::toTransaction).collect(Collectors.toSet());
    }
}
