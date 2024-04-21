package org.example.server.AuthConfigurations.AuthEntites;

import org.example.server.enums.Role;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class SignUpRequest {
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private Role role;

}
