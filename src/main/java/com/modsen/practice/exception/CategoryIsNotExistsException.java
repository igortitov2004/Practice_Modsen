package com.modsen.practice.exception;
public class CategoryIsNotExistsException extends RuntimeException {
    public CategoryIsNotExistsException(String message) {
        super(message);
    }
}
