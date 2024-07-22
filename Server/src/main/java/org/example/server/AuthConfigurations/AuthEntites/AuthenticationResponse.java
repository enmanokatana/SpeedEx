package org.example.server.AuthConfigurations.AuthEntites;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.server.enums.Role;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {

  @JsonProperty("ACCESS_TOKEN")
  private String accessToken;
  private Role role;
  private Integer userID;
  private String message;
}
