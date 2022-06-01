package com.company.bankingapp.controller;

import com.company.bankingapp.model.dto.Customer;
import com.company.bankingapp.model.request.LoginRequest;
import com.company.bankingapp.model.request.RegisterRequest;
import com.company.bankingapp.model.response.ApiDataResponse;
import com.company.bankingapp.model.response.TokenResponse;
import com.company.bankingapp.service.AuthService;
import com.company.bankingapp.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;
    private final CustomerService customerService;

    @PostMapping("/login")
    public ResponseEntity<ApiDataResponse<TokenResponse>> login(
            @Valid @RequestBody LoginRequest loginRequest) {
        TokenResponse tokenResponse = authService.login(loginRequest);
        return new ResponseEntity<>(new ApiDataResponse<>(tokenResponse), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<ApiDataResponse<Customer>> register(
            @Valid @RequestBody RegisterRequest registerRequest) {
        Customer customer = customerService.register(registerRequest);
        return new ResponseEntity<>(new ApiDataResponse<>(customer), HttpStatus.CREATED);
    }
}
