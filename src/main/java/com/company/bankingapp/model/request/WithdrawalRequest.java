package com.company.bankingapp.model.request;

import com.company.bankingapp.model.enums.CurrencyType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WithdrawalRequest {
    @NotNull
    private String accountNumber;
    @NotNull
    @Min(value = 1, message = "Withdrawal amount greater than zero")
    private BigDecimal amount;
    @NotNull
    private CurrencyType currencyType;
}
