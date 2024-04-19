package org.example.server.services;

import lombok.RequiredArgsConstructor;
import org.example.server.Dtos.OptionDto;
import org.example.server.models.Option;
import org.example.server.models.Question;
import org.example.server.models.ResponseDto;
import org.example.server.repositories.OptionRepository;
import org.example.server.repositories.QuestionRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OptionService {
    private final OptionRepository repository;
    private final QuestionRepository questionRepository;
    private ResponseDto responseDto = new ResponseDto();


    public ResponseDto getOptionsById(Long id){
        var option = repository.findById(id);
        if (option.isPresent()){
        responseDto.setResult(true);
        responseDto.setMessage("found option");
        responseDto.setResult(option.get());
        return responseDto;
        }
        responseDto.setMessage("couldn't find option with the id " + id);
        return responseDto;
    }
    public ResponseDto createOption(OptionDto optionDto){

        Optional<Question> question = questionRepository.findById(optionDto.getQuestion());
        if (question.isPresent()){
            Option option = Option.builder()
                    .question(question.get())
                    .value(optionDto.getValue())
                    .id(0L)
                    .isCorrect(optionDto.isCorrect())
                    .build();


            responseDto.setResult(repository.save(option));
            responseDto.setWorked(true);
            responseDto.setMessage("saved option successfully");
            return responseDto;

        }
        responseDto.setMessage("Question doesn't exist ");
        return responseDto;

    }

    public ResponseDto deleteOption(Long id ){
        var option = repository.findById(id);
        if(option.isPresent()){
            repository.delete(option.get());
            responseDto.setMessage("deleted successfully");
            responseDto.setWorked(true);
            return responseDto;
        }
        responseDto.setMessage("couldn't find the option with the id : " + id);
        return responseDto;
    }






}
