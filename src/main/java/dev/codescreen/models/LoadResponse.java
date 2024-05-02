package dev.codescreen.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoadResponse {
    private String messageId;
    private String userId;
    private Amount balance;
}
