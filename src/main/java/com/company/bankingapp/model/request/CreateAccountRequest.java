package com.company.bankingapp.model.request;

import com.company.bankingapp.model.enums.CurrencyType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateAccountRequest {
    private Long customerId;
    private String number;
    private BigDecimal balance;
    private CurrencyType currencyType;
}
