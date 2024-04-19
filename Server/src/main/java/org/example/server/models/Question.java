package org.example.server.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.server.enums.Type;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Question {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String description;
    private Type type;
    private String answer;
    private Integer timer ;
    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    private List<Option> options;








}
