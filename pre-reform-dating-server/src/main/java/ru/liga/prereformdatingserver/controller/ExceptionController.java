package ru.liga.prereformdatingserver.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.liga.prereformdatingserver.exception.RestOuterServiceException;
import ru.liga.prereformdatingserver.exception.StorageException;
import ru.liga.prereformdatingserver.exception.UserProfileException;
import ru.liga.prereformdatingserver.security.jwt.JwtAuthException;

@ControllerAdvice
@Slf4j
public class ExceptionController {

    @ExceptionHandler(Error.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public String onOtherError(Error error) {
        log.error("Error !!!", error);
        return "Only God can help us!";
    }

    @ExceptionHandler(UserProfileException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public String onUserProfileException(UserProfileException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(RestOuterServiceException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public String onRestOuterServiceException(RestOuterServiceException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(StorageException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public String onStorageException(StorageException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(JwtAuthException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public String onJwtAuthException(JwtAuthException ex) {
        return ex.getMessage();
    }
}
