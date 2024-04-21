package org.example.server.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Workspace {
    @Id
    @GeneratedValue
    private Long id;


    private String name;

    private String description;



    @ManyToOne
    @JsonIgnore
    private User admin;

    @ManyToMany
    @JsonIgnore
    private List<User> users;

}
