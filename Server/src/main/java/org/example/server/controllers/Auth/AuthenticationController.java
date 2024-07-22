package org.example.server.controllers.Auth;



import org.example.server.AuthConfigurations.AuthEntites.AuthenticationResponse;
import org.example.server.AuthConfigurations.AuthEntites.LogInRequest;
import org.example.server.AuthConfigurations.AuthEntites.SignUpRequest;
import org.example.server.services.Auth.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authService;

    @PostMapping("/signup")
    public ResponseEntity<AuthenticationResponse> signup(
            @RequestBody SignUpRequest request
    ) throws Exception {
        return ResponseEntity.ok(authService.signup(request));
    }
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(
            @RequestBody LogInRequest request
    ) {
        try {
            AuthenticationResponse response = authService.login(request);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException | BadCredentialsException | DisabledException | LockedException e) {
            // Handle specific exceptions with appropriate HTTP status and error message
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(AuthenticationResponse.builder()
                    .message("Invalid email or password").build());
        } catch (RuntimeException e) {
            // Handle unexpected runtime exceptions with a generic error response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(AuthenticationResponse.builder()
                    .message("An error has occured").build());
        }    }
}
