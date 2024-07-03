package org.example.server.utils;

import lombok.RequiredArgsConstructor;
import org.example.server.services.Auth.JwtService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TokenCleanUpTask {

    private final TokenRegistry tokenRegistry;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;


    @Scheduled(fixedRate = 60000) // Run every minute
    public void cleanUpExpiredTokens() {
        tokenRegistry.getActiveTokens().forEach((token, username) -> {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if (!jwtService.isTokenValid(token, userDetails)) {
                tokenRegistry.removeToken(token);
            }
        });
    }


}
