package com.company.bankingapp.security;

import com.company.bankingapp.exception.NotAcceptableTokenException;
import io.jsonwebtoken.*;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String TOKEN_PREFIX = "Bearer ";
    private final JwtTokenUtil jwtTokenUtil;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        log.debug("processing authentication for '{}'", request.getRequestURL());

        try {
            final var auth = request.getHeader(AUTHORIZATION_HEADER);
            if (Objects.nonNull(auth) && auth.startsWith(TOKEN_PREFIX)) {
                final var token = auth.substring(7);
                log.info("Token: {}", token);
                final var username = jwtTokenUtil.getUsernameFromToken(token);
                SecurityContextHolder.getContext().setAuthentication(getAuthentication(username));
            }
            filterChain.doFilter(request, response);
        } catch (ExpiredJwtException | UnsupportedJwtException |
                 MalformedJwtException | SignatureException |
                 IncorrectClaimException | IllegalArgumentException ex) {
            throw new NotAcceptableTokenException(ex.getMessage(), ex);
        }
    }

    private Authentication getAuthentication(String username) {
        final var principal = userDetailsService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(principal, null, principal.getAuthorities());
    }
}
