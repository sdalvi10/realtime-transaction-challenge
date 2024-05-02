package dev.codescreen.models;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String messageId;
    private String userId;
    private String previousBalance;
    private String newBalance;
    private String amount;
    private String debitOrCredit;
    private String timestamp;
    private String status;

    public Transaction(String messageId,
                       String userId,
                       String previousBalance,
                       String newBalance,
                       String amount,
                       String debitOrCredit,
                       String timestamp,
                       String status) {
        this.messageId = messageId;
        this.userId = userId;
        this.previousBalance = previousBalance;
        this.newBalance = newBalance;
        this.amount = amount;
        this.debitOrCredit = debitOrCredit;
        this.timestamp = timestamp;
        this.status = status;
    }
}
