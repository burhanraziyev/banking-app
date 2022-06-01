package com.company.bankingapp.model.response;

public record ApiValidationErrorResponse(
        String field,
        Object value,
        String message
) {
}
