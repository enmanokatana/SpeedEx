package org.example.server.controllers;

import lombok.RequiredArgsConstructor;
import org.example.server.models.ResponseDto;
import org.example.server.services.ExamGroupService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/ExamGroup")
@RequiredArgsConstructor
public class ExamGroupController {

    private final ExamGroupService examGroupService;



    @GetMapping("/{id}")
    public ResponseDto getExamsByGroupId(
            @PathVariable Integer id
    )
    {
        return examGroupService.getExamsByGroupId(id);
    }


}
