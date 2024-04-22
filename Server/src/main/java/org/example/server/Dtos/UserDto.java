package org.example.server.Dtos;

import lombok.Builder;
import lombok.Data;
import org.example.server.enums.Role;

@Data@Builder
public class UserDto {
    private Integer id;
    private String firstname;
    private String lastname;
    private String email;
    private String profileImg;
    private Role role;




}
