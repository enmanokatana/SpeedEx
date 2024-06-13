package org.example.server.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExamGroup {
    @Id
    @GeneratedValue
    private Integer id;

    @OneToOne
    private User Admin;

    @OneToMany
    private List<Exam> exams;




}
