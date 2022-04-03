package ru.liga.prereformdatingserver.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.liga.prereformdatingserver.exception.UserProfileException;

@ControllerAdvice
public class ExceptionController {

  /*  @ExceptionHandler(Error.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public String onOtherError(Error error) {
        return "Only God can help us!" + System.lineSeparator() + error.getMessage();
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public String onOtherError(Exception exception) {
        exception.printStackTrace();
        return exception.getMessage();
    }

    @ExceptionHandler(UserProfileException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public String onOtherError(UserProfileException e) {
        e.printStackTrace();
        return e.getMessage();
    }*/
}
