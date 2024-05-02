package dev.codescreen.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthorizationResponse {
    private String messageId;
    private String userId;
    private String responseCode;
    private Amount balance;
}
