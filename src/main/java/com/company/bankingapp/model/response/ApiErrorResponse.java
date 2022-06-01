package com.company.bankingapp.model.response;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

public record ApiErrorResponse(
        String message,
        HttpStatus status,
        List<ApiValidationErrorResponse> errors,
        String path,
        LocalDateTime timeStamp
) {
    public ApiErrorResponse(String message, HttpStatus status, String path) {
        this(message, status, List.of(), path, LocalDateTime.now());
    }

    public ApiErrorResponse(String message, HttpStatus status, List<ApiValidationErrorResponse> errors, String path) {
        this(message, status, errors, path, LocalDateTime.now());
    }
}
