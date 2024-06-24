package org.example.server.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Invitation implements Serializable {

    @Serial
    private final static long serialVersionUID = 3212019L;

    @Id
    @GeneratedValue
    private Long Id;

    private Integer userId;

    private Long workspaceId;

    private Boolean result;
}
