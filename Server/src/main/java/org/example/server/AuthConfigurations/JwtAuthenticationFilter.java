package org.example.server.AuthConfigurations;


import org.example.server.repositories.Auth.TokenRepository;
import  org.example.server.services.Auth.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.example.server.utils.TokenRegistry;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

// Intercepting HTTP requests to process the JWT.
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService; // a Bean in AppConfig
    private final TokenRepository tokenRepository;
    private final TokenRegistry tokenRegistry;
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        final String userEmail;
        final String jwt;

        logger.info("FILTER BEGIN !!! ");
        // Skip authentication when accessing a Login or a Signup form
        if (request.getServletPath().contains("/signup") || request.getServletPath().contains("/login")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Calling the next filter in the chain if no JWT is provided
        if ( authHeader == null || ! authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request,response);
            return;
        }

        jwt = authHeader.substring(7);
        userEmail = jwtService.extractUsernameFromJwt(jwt);

        // the SecurityContextHolder should be updated after every http request
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);

            // Checking if the token has been previously revoked / used
            var dbTokenValid = tokenRepository.findByToken(jwt)
                    .map(t -> !t.isRevoked())
                    .orElse(false);

            /* Checking the email claim against the stored email + Expiration date */
            if (jwtService.isTokenValid(jwt, userDetails) && dbTokenValid) {

                // Authentication object compatible with userDetails
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );

                // Adding more details to the authentication object like the IP address
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                /* Adding the Authentication Object to SecurityContextHolder
                to marque user as authenticated  */
                SecurityContextHolder.getContext().setAuthentication(authToken);

                tokenRegistry.addToken(jwt,userEmail);
            }else {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            }
        }
        filterChain.doFilter(request,response);
    }
}
