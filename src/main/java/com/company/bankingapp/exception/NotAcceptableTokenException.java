package com.company.bankingapp.exception;

public class NotAcceptableTokenException extends RuntimeException {

    public NotAcceptableTokenException(String message) {
        super(message);
    }

    public NotAcceptableTokenException(String message, Throwable cause) {
        super(message, cause);
    }
}
