package com.example.dacia.exceptionHandler;

public class DocumentStatusException extends RuntimeException {
    public DocumentStatusException(String message) {
        super(message);
    }
}
