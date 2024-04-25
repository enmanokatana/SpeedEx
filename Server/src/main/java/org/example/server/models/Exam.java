package org.example.server.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.server.enums.DifficultyLevel;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data

@Builder
public class Exam {
    @Id
    @GeneratedValue
    private Long id;

    @NotEmpty(message = "Name could Never be empty")
    private String name;

    @NotNull(message = "Timer cannot be null")
    @Positive(message = "Timer is always a positive integer")
    private Integer timer;

    @Positive(message = "Passing score must be positive")
    private Integer passingScore;

    private String description;

    private boolean randomizeQuestions ;


    private boolean isPublic ;

    private DifficultyLevel difficultyLevel = DifficultyLevel.MEDIUM;

    @OneToMany(mappedBy = "exam", cascade = CascadeType.ALL , fetch = FetchType.LAZY)
    private List<Question> questions;
    private LocalDateTime createdOn ;

    @ManyToOne
    @JsonIgnore
    private User user; // Creator of the exam can create a lot of them

    @ManyToOne
    @JsonIgnore
    private User student; //  The student who is passing in the exam


    private Boolean passed = false;// if an exam have been passed or not yet

    @ManyToOne
    @JsonIgnore
    private Workspace workspace;

}
