package com.catalogservice.domain.exception;

public class InvalidBusinessDataException extends RuntimeException {
    public InvalidBusinessDataException(String message) {
        super(message);
    }
}
