package dev.codescreen.models;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class Request {
    @NotEmpty(message = "Message ID cannot be empty")
    @NotNull(message = "Message ID cannot be null")
    @NotBlank(message = "Message ID cannot be blank")
    private String messageId;

    @NotEmpty(message = "User ID cannot be empty")
    @NotNull(message = "User ID cannot be null")
    @NotBlank(message = "User ID cannot be blank")
    private String userId;

    @Valid
    private Amount transactionAmount;
}
