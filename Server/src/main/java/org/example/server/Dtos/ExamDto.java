package org.example.server.Dtos;

import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.server.enums.DifficultyLevel;
import org.example.server.models.Question;
import org.example.server.models.Workspace;

import java.time.LocalDateTime;
import java.util.List;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExamDto {


    private  Long id;
    private String name;

    private Integer timer;

    private Integer passingScore;

    private String description;

    private boolean randomizeQuestions;

    private boolean isPublic;

    private boolean passed;
    private DifficultyLevel difficultyLevel;

    private List<Question> questions;

    private Integer user;

    private Long workspace;

    private Integer student;// nullable

    private Integer ExamGroup;






}
