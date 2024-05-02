package dev.codescreen.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Amount {
    @NotEmpty(message = "Amount cannot be empty")
    @NotNull(message = "Amount cannot be null")
    @NotBlank(message = "Amount cannot be blank")
    private String amount;

    @NotEmpty(message = "Currency cannot be empty")
    @NotNull(message = "Currency cannot be null")
    @NotBlank(message = "Currency cannot be blank")
    private String currency;

    @NotEmpty(message = "DebitOrCredit cannot be empty")
    @NotNull(message = "DebitOrCredit cannot be null")
    @NotBlank(message = "DebitOrCredit cannot be blank")
    private String debitOrCredit;
}
