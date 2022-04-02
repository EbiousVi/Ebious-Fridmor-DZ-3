package ru.liga.prereformdatingserver.exception;

public class UserProfileException extends RuntimeException{
    public UserProfileException(String message) {
        super(message);
    }

    public UserProfileException(String message, Throwable cause) {
        super(message, cause);
    }
}
