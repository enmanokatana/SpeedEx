package org.example.server.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Invitation {

    @Id
    @GeneratedValue
    private Long Id;

    private Integer userId;

    private Long workspaceId;

    private Boolean result;
}
