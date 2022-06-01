package com.company.bankingapp.client;

import com.company.bankingapp.client.model.CurrencyRate;
import com.company.bankingapp.model.enums.CurrencyType;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class CurrencyClient {

    private final RestTemplate restTemplate;

    @Value("${currency.apikey}")
    private String apikey;

    private static final String GET_RATE_URL = "https://api.apilayer.com/fixer/convert?to=%s&from=%s&amount=%s";

    public ResponseEntity<CurrencyRate> convert(CurrencyType from, CurrencyType to, BigDecimal amount) {
        final var url = String.format(GET_RATE_URL, from.name(), to.name(), amount);
        var headers = new HttpHeaders();
        headers.add("apikey", "S8mEhqOeIxymWxqgaFu4BqbL64tNhQtZ");
        return restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), CurrencyRate.class);
    }

}
