package org.example.server.controllers.Auth;



import org.example.server.AuthConfigurations.AuthEntites.AuthenticationResponse;
import org.example.server.AuthConfigurations.AuthEntites.LogInRequest;
import org.example.server.AuthConfigurations.AuthEntites.SignUpRequest;
import org.example.server.services.Auth.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authservice;

    @PostMapping("/signup")
    public ResponseEntity<AuthenticationResponse> signup(
            @RequestBody SignUpRequest request
    )
    {
        return ResponseEntity.ok(authservice.signup(request));
    }
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(
            @RequestBody LogInRequest request
    ) {
        return ResponseEntity.ok(authservice.login(request));
    }
}
