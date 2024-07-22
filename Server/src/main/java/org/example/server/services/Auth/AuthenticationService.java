package org.example.server.services.Auth;


import org.example.server.AuthConfigurations.AuthEntites.*;
import org.example.server.models.User;
import org.example.server.repositories.Auth.TokenRepository;
import org.example.server.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final TokenRepository tokenRepository;
    public AuthenticationResponse signup(SignUpRequest request) throws Exception {

        // this will be replaced with DTOs or Optional Objects in the next version
        if (request.getFirstname() == null ||
                request.getLastname() == null ||
                request.getEmail() == null ||
                request.getPassword() == null ||
                request.getRole() == null)
            throw new IllegalArgumentException(" ALL FIELDS ARE REQUIRED"+request);

        var user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .profileImg(null)
                .exams(null)
                .myWorkspaces(null)
                .passingExams(null)
                .build();
        var dbsaveduser = userRepository.save(user);
        revokeAllUserTokens(dbsaveduser);
        var jwToken =jwtService.generateToken(user);
        saveUserToken(dbsaveduser, jwToken);
        return AuthenticationResponse.builder()
                .accessToken(jwToken)
                .role(user.getRole())
                .build();
    }

    @Deprecated
    public AuthenticationResponse loginOld(LogInRequest request) throws Exception {
        // authenticating user using Authentication Provider mechanism
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),request.getPassword())
        );
        var user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        var jwToken =jwtService.generateToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwToken);
        return AuthenticationResponse.builder()
                .accessToken(jwToken)
                .role(user.getRole())
                .userID(user.getId())
                .build();
    }


    public AuthenticationResponse login(LogInRequest request) throws AuthenticationException {
        // authenticating user using Authentication Provider mechanism
        ValidateRequest(request);
        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(), request.getPassword())
            );

            var user = userRepository.findByEmail(request.getEmail())
                    .orElseThrow(
                            () -> new UsernameNotFoundException("User not found"));

            var jwToken = jwtService.generateToken(user);

            revokeAllUserTokens(user);

            saveUserToken(user, jwToken);

            return AuthenticationResponse.builder()
                    .accessToken(jwToken)
                    .role(user.getRole())
                    .userID(user.getId())
                    .build();

        }catch (BadCredentialsException e){
            throw new BadCredentialsException("Invalid email or password");
        }catch (DisabledException e) {
            throw new DisabledException("User account is disabled");
        } catch (LockedException e) {
            throw new LockedException("User account is locked");
        } catch (AuthenticationCredentialsNotFoundException e) {
            throw new AuthenticationCredentialsNotFoundException("Authentication credentials not found");
        } catch (AuthenticationException e) {
            throw new RuntimeException("Authentication process failed", e);  // Catching other possible AuthenticationExceptions
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }





    private void ValidateRequest(LogInRequest request){
        if (request.getEmail() == null || request.getEmail().isEmpty()) {
            throw new IllegalArgumentException("Email must not be empty");
        }
        if (request.getPassword() == null || request.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Password must not be empty");
        }

    }

    private void saveUserToken(User dbsaveduser, String Token) {
        var token = org.example.server.AuthConfigurations.AuthEntites.Token.builder()
                .user(dbsaveduser)
                .tokenType(TokenType.BEARER)
                .revoked(false)
                .token(Token)
                .build();
        tokenRepository.save(token);
    }
    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

}
