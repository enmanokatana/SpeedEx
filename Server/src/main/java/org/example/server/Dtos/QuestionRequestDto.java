package org.example.server.Dtos;

import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import lombok.Data;
import org.example.server.enums.Type;
import org.example.server.models.Option;

import java.util.List;
@Data
public class QuestionRequestDto {
    private String name;
    private String description;
    private Type type;
    private String answer;
    private Integer timer ;
    private List<Option> options;
}
