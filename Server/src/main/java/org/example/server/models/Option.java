package org.example.server.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Option {
    @Id
    @GeneratedValue
    private Long id;

    @NotEmpty(message = "value of option cannot be empty")
    private String value ;



    private boolean isCorrect;

    @ManyToOne
    @JsonIgnore
    private Question question;



}
