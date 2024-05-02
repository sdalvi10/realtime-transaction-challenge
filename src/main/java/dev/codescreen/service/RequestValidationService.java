package dev.codescreen.service;

import dev.codescreen.errors.InvalidAmountException;
import dev.codescreen.errors.InvalidMessageIdException;
import dev.codescreen.errors.InvalidTransactionTypeException;
import dev.codescreen.errors.InvalidUserException;
import dev.codescreen.models.MessageIdEntity;
import dev.codescreen.models.Request;
import dev.codescreen.models.TransactionType;
import dev.codescreen.models.UserEntity;
import dev.codescreen.persist.MessageIdEntityRepository;
import dev.codescreen.persist.UserEntityRepository;
import org.springframework.stereotype.Service;

@Service
public class RequestValidationService {

    UserEntityRepository userEntityRepository;
    MessageIdEntityRepository messageIdEntityRepository;

    public RequestValidationService(UserEntityRepository userEntityRepository,
                                    MessageIdEntityRepository messageIdEntityRepository) {
        this.userEntityRepository = userEntityRepository;
        this.messageIdEntityRepository = messageIdEntityRepository;
    }

    public void validate(Request request, TransactionType expectedType) {
        validateUser(request);
        validateAmount(request);
        validateTransactionType(request, expectedType);
        validateMessageId(request);
    }

    private void validateMessageId(Request request) {
        MessageIdEntity messageIdEntity = messageIdEntityRepository.findByMessageId(request.getMessageId());
        if (messageIdEntity != null) {
            throw new InvalidMessageIdException("A transaction with this messageId already exists. Please enter a new messageId in the url.");
        }
        messageIdEntityRepository.save(new MessageIdEntity(request.getMessageId()));
    }

    private void validateTransactionType(Request request, TransactionType expectedType) {
        TransactionType transactionType;
        try {
            transactionType = TransactionType.valueOf(request.getTransactionAmount().getDebitOrCredit().toUpperCase());
        } catch (Exception e) {
            throw new InvalidTransactionTypeException("Invalid transaction type. Accepted value - " + expectedType.toString());
        }

        String requestType;
        if (expectedType == TransactionType.DEBIT) {
            requestType = "authorization";
        } else {
            requestType = "load";
        }

        if (transactionType != expectedType) {
            throw new InvalidTransactionTypeException("Transaction type for " + requestType + " request should be '" + expectedType.toString() + "'");
        }
    }

    private void validateAmount(Request request) {
        try {
            Double amount = Double.parseDouble(request.getTransactionAmount().getAmount());
        } catch (Exception e) {
            throw new InvalidAmountException("Please enter a valid amount.");
        }
    }

    private void validateUser(Request request) {
        UserEntity userEntity = userEntityRepository.findByUserId(request.getUserId());
        if (userEntity == null) {
            throw new InvalidUserException("User does not exist");
        }
    }
}
