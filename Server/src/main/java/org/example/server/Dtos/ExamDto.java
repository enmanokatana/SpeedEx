package org.example.server.Dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.server.enums.DifficultyLevel;
import org.example.server.enums.Result;
import org.example.server.models.Question;

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
    private LocalDateTime passingDate ;
    private Result result ;
    private LocalDateTime createdOn ;






}
