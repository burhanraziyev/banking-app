package com.company.bankingapp.model.dto;

import java.time.LocalDateTime;
import java.util.Set;

public record Customer(
        Long id,
        String username,
        String password,
        String firstName,
        String lastName,
        Set<Account> accounts,
        Set<Role> roles,
        String createdBy,
        LocalDateTime createdDate,
        String lastModifiedBy,
        LocalDateTime lastModifiedDate) {
}
