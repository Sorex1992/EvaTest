package com.eva.test.exception;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
