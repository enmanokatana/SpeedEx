package org.example.server.services.Auth;

import org.example.server.repositories.Auth.TokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {

    private static final Logger logger = LogManager.getLogger(LogoutService.class);

    private final TokenRepository tokenRepository;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }
        jwt = authHeader.substring(7);
        var dbToken = tokenRepository.findByToken(jwt)
                .orElse(null);
        var dbTokenValid = tokenRepository.findByToken(jwt)
                .map(t -> !t.isRevoked())
                .orElse(false);

        if (dbToken != null ){
            if( dbTokenValid ) {
                dbToken.setRevoked(true);
                tokenRepository.save(dbToken); // updating the db token
                logger.info("clearing context");
                SecurityContextHolder.clearContext(); // logging out
            } else{
                logger.error("Revoked JWT");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            }
        }

        }
    }
