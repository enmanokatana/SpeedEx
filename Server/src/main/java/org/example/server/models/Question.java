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
import org.example.server.enums.Type;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Question {

    @Id
    @GeneratedValue
    private Long id;

    @NotEmpty(message = "Name cannot be empty")
    private String name;

    private String description;

    // Consider adding validation for description and answer fields as well

    @NotNull(message = "Type cannot be null")
    private Type type;

    @NotEmpty(message = "Answer cannot be empty")
    private String answer;



    private String userAnswer;

    private Integer timer;
    //score needs to be added here
    @Positive(message = "Score cannot be negative")
    private Integer score;

    @ManyToOne
    @JsonIgnore
    private Exam exam;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    private List<Option> options;//control it later

    @Positive
    private Integer userOption;

    private DifficultyLevel difficultyLevel;


    public Integer getTrueOption(){

        for (int i = 0; i < options.size(); i++) {
            if (options.get(i).isCorrect()) return i+1;

        }

        return 0;

    }
}
