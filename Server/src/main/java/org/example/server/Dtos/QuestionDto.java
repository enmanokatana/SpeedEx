package org.example.server.Dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.server.enums.DifficultyLevel;
import org.example.server.enums.Type;
import org.example.server.models.Option;

import java.util.List;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestionDto {
    private Long id;
    private String name;
    private String description;
    private Type type;
    private String answer;
    private Integer timer ;
    private List<Option> options;
    private Integer userOption;
    private String userAnswer;
    private Integer score;
    private Long exam;
    private DifficultyLevel difficultyLevel;
}
