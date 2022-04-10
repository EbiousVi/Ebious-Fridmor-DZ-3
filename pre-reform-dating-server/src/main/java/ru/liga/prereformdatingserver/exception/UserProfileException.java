package ru.liga.prereformdatingserver.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class UserProfileException extends RuntimeException {

    private HttpStatus httpStatus;

    public UserProfileException(String message) {
        super(message);
    }

    public UserProfileException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public UserProfileException(String message, Throwable cause) {
        super(message, cause);
    }
}
