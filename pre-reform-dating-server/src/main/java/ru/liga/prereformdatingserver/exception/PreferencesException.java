package ru.liga.prereformdatingserver.exception;

public class PreferencesException extends RuntimeException{
    public PreferencesException(String message) {
        super(message);
    }

    public PreferencesException(String message, Throwable cause) {
        super(message, cause);
    }
}
