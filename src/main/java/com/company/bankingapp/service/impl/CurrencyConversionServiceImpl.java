package com.company.bankingapp.service.impl;

import com.company.bankingapp.client.CurrencyClient;
import com.company.bankingapp.client.model.CurrencyRate;
import com.company.bankingapp.exception.ConversionException;
import com.company.bankingapp.model.enums.CurrencyType;
import com.company.bankingapp.service.CurrencyConversionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CurrencyConversionServiceImpl implements CurrencyConversionService {
    private final CurrencyClient currencyClient;

    @Override
    public BigDecimal convert(CurrencyType from, CurrencyType to, BigDecimal amount) {
        Optional<CurrencyRate> currencyRate = Optional.ofNullable(currencyClient.convert(from, to, amount).getBody());
        return currencyRate.map(CurrencyRate::result).orElseThrow(
                () -> new ConversionException(
                        String.format("From %s to %s for %s converted not success",from,to,amount)));
    }
}
