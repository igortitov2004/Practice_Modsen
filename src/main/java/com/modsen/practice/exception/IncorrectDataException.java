package com.modsen.practice.exception;

public class IncorrectDataException extends  RuntimeException {
    @Override
    public String getMessage() {
        return "Неверные данные";
    }
}
