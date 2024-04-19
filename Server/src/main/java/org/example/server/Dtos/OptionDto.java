package org.example.server.Dtos;

import jakarta.persistence.ManyToOne;
import lombok.Data;
import org.example.server.models.Question;
@Data
public class OptionDto {

    private String value ;

    private boolean isCorrect;

    private Long question;
}
