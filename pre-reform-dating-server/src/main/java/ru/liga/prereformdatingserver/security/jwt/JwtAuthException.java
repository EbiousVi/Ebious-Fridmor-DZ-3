package ru.liga.prereformdatingserver.security.jwt;

public class JwtAuthException extends RuntimeException{

    public JwtAuthException(String message) {
        super(message);
    }
}
