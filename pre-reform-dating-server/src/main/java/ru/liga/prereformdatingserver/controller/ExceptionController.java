package ru.liga.prereformdatingserver.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.liga.prereformdatingserver.exception.RestOuterServiceException;
import ru.liga.prereformdatingserver.exception.AvatarNotFoundException;
import ru.liga.prereformdatingserver.exception.UserProfileException;
import ru.liga.prereformdatingserver.security.jwt.JwtAuthException;

import java.util.Iterator;

@ControllerAdvice
@Slf4j
public class ExceptionController {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Iterator<ObjectError> iterator = ex.getBindingResult().getAllErrors().iterator();
        StringBuilder message = new StringBuilder();
        while (iterator.hasNext()) {
            message.append(iterator.next().getDefaultMessage()).append(System.lineSeparator());
        }
        message.deleteCharAt(message.lastIndexOf(System.lineSeparator()));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message.toString());
    }

    @ExceptionHandler(UserProfileException.class)
    public ResponseEntity<String> handleUserProfileException(UserProfileException ex) {
        return ResponseEntity.status(ex.getHttpStatus()).body(ex.getMessage());
    }

    @ExceptionHandler(RestOuterServiceException.class)
    public ResponseEntity<String> handleRestOuterServiceException(RestOuterServiceException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
    }

    @ExceptionHandler(AvatarNotFoundException.class)
    public ResponseEntity<String> handleStorageException(AvatarNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
    }

    @ExceptionHandler(JwtAuthException.class)
    public ResponseEntity<String> handleJwtAuthException(JwtAuthException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<String> handleUsernameNotFoundException(UsernameNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
    }
}
