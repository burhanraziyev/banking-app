package com.company.bankingapp.service;

import com.company.bankingapp.model.enums.CurrencyType;

import java.math.BigDecimal;

public interface CurrencyConversionService {
    BigDecimal convert(CurrencyType from, CurrencyType to,BigDecimal amount);
}
