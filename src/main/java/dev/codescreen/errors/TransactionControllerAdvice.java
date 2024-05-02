package dev.codescreen.errors;

import dev.codescreen.models.Error;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.ArrayList;

@RestControllerAdvice
public class TransactionControllerAdvice {

    @ExceptionHandler(InvalidAmountException.class)
    public ResponseEntity<Error> handleInvalidAmountException(InvalidAmountException invalidAmountException) {
        return new ResponseEntity<>(new Error(invalidAmountException.getMessage(), "400"), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidTransactionTypeException.class)
    public ResponseEntity<Error> handleInvalidTransactionTypeException(InvalidTransactionTypeException exception) {
        return new ResponseEntity<>(new Error(exception.getMessage(), "400"), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Error> handleInvalidArgument(MethodArgumentNotValidException ex) {
        ArrayList<String> errors = new ArrayList<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String errorMessage = error.getDefaultMessage();
            errors.add(errorMessage);
        });
        Error error = new Error(errors.get(0), "400");
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidUserException.class)
    public ResponseEntity<Error> handleInvalidUserException(InvalidUserException exception) {
        return new ResponseEntity<>(new Error(exception.getMessage(), "400"), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidMessageIdException.class)
    public ResponseEntity<Error> handleInvalidMessageIdException(InvalidMessageIdException exception) {
        return new ResponseEntity<>(new Error(exception.getMessage(), "400"), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<Error> handleNoResourceFoundException(NoResourceFoundException exception) {
        return new ResponseEntity<>(new Error("Please enter a valid url. Load and Authorization request must include message id as a parameter.", "400"), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Error> defaultExceptionHandler(Exception e) {
        return new ResponseEntity<>(new Error("Unknown server error occurred.", "500"), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
