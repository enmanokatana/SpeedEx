package org.example.server.services;

import lombok.RequiredArgsConstructor;
import org.example.server.models.Question;
import org.example.server.models.ResponseDto;
import org.example.server.repositories.QuestionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepository repository;
    private final ResponseDto responseDto = new ResponseDto();


    public ResponseDto getAllQuestions()throws Exception{
        List<Question> res = repository.findAll();
        if (res.isEmpty()){
            responseDto.setMessage("No questions were found ");
            responseDto.setWorked(false);
            responseDto.setResult(null);
        }
        responseDto.setMessage("Found Questions Successfully ");
        responseDto.setWorked(true);
        responseDto.setResult(res);
        return responseDto;
    }

    public ResponseDto getQuestionById(Long id){
        Optional<Question> question = repository.findById(id);
        if (question.isPresent()){
            responseDto.setResult(question.get());
            responseDto.setMessage("Question found  " );
            responseDto.setWorked(true);
            return  responseDto;
        }
        responseDto.setMessage("No Question with this id "+id+" was found ");

        return responseDto;
    }
    public ResponseDto CreateQuestion(Question question)throws Exception{
        responseDto.setResult(repository.save(question));
        responseDto.setWorked(true);
        responseDto.setMessage("Question Added Successfully ");
        return responseDto;

    }
    public  ResponseDto DeleteQuestion(Long id){
        Optional<Question> question = repository.findById(id);
        if (question.isPresent()){
            repository.delete(question.get());
            responseDto.setMessage("Deleted Question with the id  : " + id);
            responseDto.setWorked(true);
            return responseDto;

        }
        responseDto.setMessage("$No Question with this id {id} was found ");

        return responseDto;

    }


}
