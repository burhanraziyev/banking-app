package com.company.bankingapp.service;

import com.company.bankingapp.model.request.LoginRequest;
import com.company.bankingapp.model.response.TokenResponse;

public interface AuthService {

    TokenResponse login(LoginRequest loginRequest);
}
