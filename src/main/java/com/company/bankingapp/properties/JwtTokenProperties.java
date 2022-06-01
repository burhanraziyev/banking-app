package com.company.bankingapp.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class JwtTokenProperties {

    @Value("${application.security.secret-key}")
    private String secretKey;

    @Value("${application.security.expiration}")
    private Long accessTokenExpiration;
}
