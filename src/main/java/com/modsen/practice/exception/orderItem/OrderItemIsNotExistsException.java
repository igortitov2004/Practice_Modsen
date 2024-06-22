package com.modsen.practice.exception.orderItem;
public class OrderItemIsNotExistsException extends RuntimeException {
    public OrderItemIsNotExistsException(String message) {
        super(message);
    }
}
