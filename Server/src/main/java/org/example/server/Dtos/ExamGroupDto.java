package org.example.server.Dtos;


import lombok.Data;

import java.util.List;
@Data
public class ExamGroupDto {

    private Integer id;


    private Integer Admin;


    private List<Long> exams;

}
