package ru.liga.prereformdatingserver.exception;

public class AvatarNotFoundException extends RuntimeException {
    public AvatarNotFoundException(String message) {
        super(message);
    }

    public AvatarNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
