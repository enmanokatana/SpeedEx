package org.example.server.Dtos;

import lombok.Data;
import org.example.server.models.User;

import java.util.List;
@Data
public class WorkSpaceDto {
    private String name;

    private String description;

    private Integer admin;

    private List<Integer> users;
}
