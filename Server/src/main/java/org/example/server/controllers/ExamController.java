package org.example.server.controllers;

import lombok.RequiredArgsConstructor;
import org.example.server.Dtos.ExamDto;
import org.example.server.models.Exam;
import org.example.server.models.ResponseDto;
import org.example.server.services.ExamService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/Exam")
@RequiredArgsConstructor
public class ExamController {
    private final ExamService examService;

    @GetMapping("/{id}")
    public ResponseDto getExamById(
            @PathVariable Long id
    ){
        return examService.findExamById(id);

    }
    @PostMapping
    public  ResponseDto createExam(
            @RequestBody ExamDto exam

            ){
        return examService.createExamNew(exam);
    }
    @DeleteMapping("/{id}")
    public ResponseDto deleteExam(
            @PathVariable Long id
    ){
        return examService.deleteExam(id);
    }

    @GetMapping("/user/{id}")
    public ResponseDto GetExamsByuSER(
            @PathVariable Integer id

    )
    {
        return examService.findExamsByUser(id);
    }
    @GetMapping("/questionsIds/{id}")
    public ResponseDto GetQuestionIdsByExam(
            @PathVariable Long id

    )
    {
        return examService.getQuestionIdsByExamId(id);
    }



}
