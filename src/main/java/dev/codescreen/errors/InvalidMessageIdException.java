package dev.codescreen.errors;

public class InvalidMessageIdException extends RuntimeException{
    public InvalidMessageIdException(String message) {
        super(message);
    }
}
