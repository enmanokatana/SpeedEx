package org.example.server.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExamGroup implements Serializable {

    @Serial
    private static final long serialVersionUID =21091911L;
    @Id
    @GeneratedValue
    private Integer id;
//
//    @ManyToOne
//    private User Admin;

    @OneToMany
    @JsonManagedReference
    private List<Exam> exams = new ArrayList<>();




}
