package com.qeema.order_task.exception;

public class ProductNameAlreadyExistsException extends RuntimeException {
    public ProductNameAlreadyExistsException(String message) {
        super(message);
    }
}