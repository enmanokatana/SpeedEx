package org.example.server.controllers;

import lombok.RequiredArgsConstructor;
import org.example.server.Dtos.QuestionDto;
import org.example.server.models.Question;
import org.example.server.models.ResponseDto;
import org.example.server.services.QuestionService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/Question")
@RequiredArgsConstructor
public class QuestionController {
    private final QuestionService questionService;

    @GetMapping
    public ResponseDto GetAllQuestions() throws Exception {
        return questionService.getAllQuestions();
    }
    @GetMapping("/{id}")
    public ResponseDto GetAllQuestions(
            @PathVariable Long id
    ) {
        return questionService.getQuestionById(id);
    }    @PostMapping
    public ResponseDto AddQuestion(
            @RequestBody QuestionDto question
            )throws Exception{
        return questionService.CreateQuestion(question
        );
    }
    @DeleteMapping("/{id}")
    public ResponseDto DeleteQuestion(@PathVariable  Long id){
        return questionService.DeleteQuestion(id);
    }

}
