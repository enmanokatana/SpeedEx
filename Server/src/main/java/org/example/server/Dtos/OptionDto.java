package org.example.server.Dtos;

import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Data;
import org.example.server.models.Question;
@Data
@Builder
public class OptionDto {

    private String value ;

    private boolean isCorrect;

    private Long question;
}
