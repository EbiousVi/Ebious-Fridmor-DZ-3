package ru.liga.prereformdatingserver.exception;

public class RestOuterServiceException extends RuntimeException{
    public RestOuterServiceException(String message) {
        super(message);
    }

    public RestOuterServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
