package com.modsen.practice.exception;

public class IncorrectDataException extends  RuntimeException {
    public IncorrectDataException(String message) {
        super(message);
    }
}
