package com.company.bankingapp.service.impl;

import com.company.bankingapp.model.request.LoginRequest;
import com.company.bankingapp.model.response.TokenResponse;
import com.company.bankingapp.security.JwtTokenUtil;
import com.company.bankingapp.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserDetailsService userDetailsService;

    @Override
    public TokenResponse login(LoginRequest loginRequest) {
        authenticate(loginRequest);
        UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getUsername());
        final var accessToken = jwtTokenUtil.generateToken(userDetails);
        return new TokenResponse(accessToken);
    }

    private void authenticate(LoginRequest loginRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        } catch (DisabledException e) {
            throw new DisabledException("User is disabled", e);
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Bad credentials", e);
        }
    }
}
