package com.modsen.practice.exception.product;

public class ProductIsNotExistsException extends RuntimeException {
    public ProductIsNotExistsException(String message) {
        super(message);
    }
}
