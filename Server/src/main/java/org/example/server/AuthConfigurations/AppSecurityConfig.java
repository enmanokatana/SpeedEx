package org.example.server.AuthConfigurations;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import java.util.List;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class AppSecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final  List<String> WHITE_LIST ; // Will contain endpoints that dont require authentication
    private final LogoutHandler logoutHandler;

    // At startup spring security looks for a bean of type SecurityFilterChain
    @Bean public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {


        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req -> req
                        .requestMatchers("/signup")
                        .permitAll()
                        .requestMatchers("/swagger-ui/index.html"
                        ,"/v2/api-docs/**"
                                ,"/v3/api-docs/**"
                                ,"/swagger-resources"
                                ,"/swagger-resources/**"
                                ,"/confguration/ui"
                                ,"configuration/security"
                                ,"swagger-ui/**"
                                ,"webjars/**"
                                ,"swagger-ui.html"
                                ,"/app"
                                ,"/api/v1/Invitations/**"
                        )

                        .permitAll()
                        .requestMatchers(HttpMethod.POST,"/api/v1/Workspace/**",
                                "/api/v1/Exam",
                                "api/v1/Question",
                                "api/v1/Option")


                        .hasAnyAuthority("ADMIN","USER")
                        .requestMatchers(HttpMethod.PUT,"/api/v1/Workspace/**",
                                "/api/v1/Exam",
                                "api/v1/Question",
                                "api/v1/Option")


                        .hasAnyAuthority("ADMIN")
                        .requestMatchers(HttpMethod.DELETE,"/api/v1/Workspace/**",
                                "/api/v1/Exam/**",
                                "api/v1/Question/**",
                                "api/v1/Option/**")


                        .hasAnyAuthority("ADMIN")
                        .requestMatchers("/login")
                        .permitAll()
                        .requestMatchers("/error")
                        .permitAll()
                       // .requestMatchers("api/v1/Invitations/**")
                        //.authenticated()
                        .anyRequest()
                        .authenticated()


                )
                // No state saving : Each http request should be authenticated
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                // Provides the mechanism for the Authentication Manager to validate user credentials.
                .authenticationProvider(authenticationProvider)
                // Prioritizing the processing of JWT-based authentication over form-based authentication
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .logout(logout ->
                        logout.logoutUrl("/logout")
                                .addLogoutHandler(logoutHandler)
                                .logoutSuccessHandler(
                                        (request, response, authentication) -> SecurityContextHolder.clearContext())
                );

                return http.build();

    }



}
