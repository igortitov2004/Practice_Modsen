package com.modsen.practice.exception;

public class UserExistenceException extends RuntimeException {
    @Override
    public String getMessage() {
        return "Такого пользователя не существует";
    }
}
