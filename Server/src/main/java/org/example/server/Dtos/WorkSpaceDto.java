package org.example.server.Dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.server.models.User;

import java.util.List;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WorkSpaceDto {
    private Long id;
    private String name;

    private String description;

    private Integer admin;

    private List<Integer> users;
    private String image;
    private List<Long> exams;
    private String code;
}
