package org.example.server.controllers;

import lombok.RequiredArgsConstructor;
import org.example.server.Dtos.QuestionDto;
import org.example.server.models.Question;
import org.example.server.models.ResponseDto;
import org.example.server.services.QuestionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseDto GetQuestionDtoById(
            @PathVariable Long id
    ) {
        return questionService.getQuestionDtobyId(id);
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


    @PostMapping("/passExam")
    public ResponseDto Pass(
            @RequestBody List<QuestionDto> questions
    ){
        return questionService.PassExamUpdateQuestion(questions);
    }

}
