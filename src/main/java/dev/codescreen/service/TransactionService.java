package dev.codescreen.service;

import dev.codescreen.dto.Event;
import dev.codescreen.models.*;
import dev.codescreen.persist.UserEntityRepository;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TransactionService {

    private final UserEntityRepository userEntityRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private Status transactionStatus;

    public TransactionService(UserEntityRepository userEntityRepository, KafkaTemplate<String, Object> kafkaTemplate) {
        this.userEntityRepository = userEntityRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    public LoadResponse loadAmount(Request request) {
        UserEntity userEntity = userEntityRepository.findByUserId(request.getUserId());

        Double prevBalance = Double.parseDouble(userEntity.getBalance());
        Double transactionAmount = Double.parseDouble(request.getTransactionAmount().getAmount());

        transactionStatus = calculateNewBalance(userEntity, prevBalance, transactionAmount, TransactionType.CREDIT);

        userEntityRepository.save(userEntity);

        publishTransactionEvent(request, userEntity, prevBalance, transactionAmount, TransactionType.CREDIT);

        return new LoadResponse(
                request.getMessageId(),
                request.getUserId(),
                new Amount(
                        userEntity.getBalance(),
                        request.getTransactionAmount().getCurrency().toUpperCase(),
                        TransactionType.CREDIT.toString()));
    }

    public AuthorizationResponse unloadAmount(Request request) {
        UserEntity userEntity = userEntityRepository.findByUserId(request.getUserId());
        Double prevBalance = Double.parseDouble(userEntity.getBalance());
        Double transactionAmount = Double.parseDouble(request.getTransactionAmount().getAmount());

        transactionStatus = calculateNewBalance(userEntity, prevBalance, transactionAmount, TransactionType.DEBIT);

        userEntityRepository.save(userEntity);

        publishTransactionEvent(request, userEntity, prevBalance, transactionAmount, TransactionType.DEBIT);

        return new AuthorizationResponse(
                request.getMessageId(),
                request.getUserId(),
                transactionStatus.toString(),
                new Amount(
                        userEntity.getBalance(),
                        request.getTransactionAmount().getCurrency().toUpperCase(),
                        TransactionType.DEBIT.toString()
                )
        );
    }

    private void publishTransactionEvent(Request request,
                                         UserEntity userEntity,
                                         Double prevBalance,
                                         Double transactionAmount,
                                         TransactionType transactionType) {
        Event event = new Event("CreateTransaction", new Transaction(
                request.getMessageId(),
                request.getUserId(),
                String.format("%.2f", prevBalance),
                userEntity.getBalance(),
                String.format("%.2f", transactionAmount),
                transactionType.toString(),
                LocalDateTime.now().toString(),
                transactionStatus.toString()));
        kafkaTemplate.send("transaction-event-topic", event);
    }


    private Status calculateNewBalance(UserEntity userEntity, Double prevBalance, Double transactionAmount, TransactionType transactionType) {
        Double newBalance;
        Status status = Status.APPROVED;

        if (transactionAmount <= 0) {
            return Status.DECLINED;
        }

        if (transactionType == TransactionType.DEBIT) {
            if (transactionAmount > prevBalance) {
                status = Status.DECLINED;
            }
            newBalance = prevBalance - transactionAmount;
        } else {
            newBalance = prevBalance + transactionAmount;
        }

        if (status == Status.APPROVED) {
            userEntity.setBalance(String.format("%.2f", newBalance));
        }

        return status;
    }

    public Status getTransactionStatus() {
        return transactionStatus;
    }

}
