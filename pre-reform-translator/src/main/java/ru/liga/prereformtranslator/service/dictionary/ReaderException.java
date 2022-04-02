package ru.liga.prereformtranslator.service.dictionary;

public class ReaderException extends RuntimeException{

    public ReaderException(String message) {
        super(message);
    }

    public ReaderException(String message, Throwable cause) {
        super(message, cause);
    }
}
