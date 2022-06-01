package com.company.bankingapp.model.dto;

import com.company.bankingapp.model.enums.RoleType;

import java.time.LocalDateTime;

public record Role(
        Long id,
        RoleType roleType,
        String createdBy,
        LocalDateTime createdDate,
        String lastModifiedBy,
        LocalDateTime lastModifiedDate) {
}
