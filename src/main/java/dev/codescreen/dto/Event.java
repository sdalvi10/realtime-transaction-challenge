package dev.codescreen.dto;

import dev.codescreen.models.Transaction;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Event {
    private String type;
    private Transaction transactionEntity;
}
