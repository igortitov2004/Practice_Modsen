package com.modsen.practice.exception;

public class UserIsNotExistsException extends RuntimeException {
    public UserIsNotExistsException(String message) {
        super(message);
    }
}
