package com.company.bankingapp.model.request;

import com.company.bankingapp.model.enums.CurrencyType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionRequest {
    @NotNull @NotBlank @NotEmpty
    private String fromAccountNumber;
    @NotNull @NotBlank @NotEmpty
    private String toAccountNumber;
    @NotNull
    private BigDecimal amount;
    @NotNull
    private CurrencyType currencyType;
}
