package dev.codescreen.controllers;

import dev.codescreen.models.*;
import dev.codescreen.service.RequestValidationService;
import dev.codescreen.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
public class TransactionController {
    TransactionService transactionService;
    RequestValidationService requestValidationService;

    public TransactionController(TransactionService transactionService, RequestValidationService requestValidationService) {
        this.transactionService = transactionService;
        this.requestValidationService = requestValidationService;
    }

    @GetMapping("/ping")
    @ResponseStatus(HttpStatus.OK)
    public Ping ping() {
        LocalDateTime localDateTime = LocalDateTime.now();
        return new Ping(localDateTime.toString());
    }

    @PutMapping("/load/{messageId}")
    public ResponseEntity<LoadResponse> loadAmount(
            @PathVariable(required = true) String messageId,
            @Valid @RequestBody Request request
    ) {
        request.setMessageId(messageId);
        requestValidationService.validate(request, TransactionType.CREDIT);
        LoadResponse loadResponse = transactionService.loadAmount(request);
        Status transactionStatus = transactionService.getTransactionStatus();
        HttpStatus httpStatus = transactionStatus == Status.APPROVED ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(loadResponse, httpStatus);
    }

    @PutMapping("/authorization/{messageId}")
    public ResponseEntity<AuthorizationResponse> unloadAmount(
            @PathVariable(required = true) String messageId,
            @Valid @RequestBody Request request) {
        request.setMessageId(messageId);
        requestValidationService.validate(request, TransactionType.DEBIT);
        AuthorizationResponse authorizationResponse = transactionService.unloadAmount(request);
        Status transactionStatus = transactionService.getTransactionStatus();
        HttpStatus httpStatus = transactionStatus == Status.APPROVED ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(authorizationResponse, httpStatus);
    }
}
