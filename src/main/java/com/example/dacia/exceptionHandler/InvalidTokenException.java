package com.example.dacia.exceptionHandler;

public class InvalidTokenException extends RuntimeException {
    public InvalidTokenException(String message) { super(message); }
}
