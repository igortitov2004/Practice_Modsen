package com.modsen.practice.exception;

public class CategoryExistenceException extends RuntimeException {
    @Override
    public String getMessage() {
        return "Такой категории не существует";
    }
}
