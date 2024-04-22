package org.example.server.services;

import lombok.RequiredArgsConstructor;
import org.example.server.Dtos.OptionDto;
import org.example.server.models.Option;
import org.example.server.models.Question;
import org.example.server.models.ResponseDto;
import org.example.server.repositories.OptionRepository;
import org.example.server.repositories.QuestionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
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
        responseDto.setWorked(false);
        responseDto.setResult(null);
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
        } responseDto.setWorked(false);
        responseDto.setResult(null);
        responseDto.setMessage("couldn't find the option with the id : " + id);
        return responseDto;
    }
    public ResponseDto modifyOption(Option option){
        Optional<Option> oldOp = repository.findById(option.getId());
        if (oldOp.isPresent())
        {
            responseDto.setResult(repository.save(option));
            responseDto.setWorked(true);
            responseDto.setMessage("Changed Option Successfully");
            return responseDto;
        } responseDto.setWorked(false);
        responseDto.setResult(null);
        responseDto.setMessage("Couldn't find the wanted Option");
        return responseDto;
    }

    public ResponseDto getOptionsByQuestionId(Long id){

        var question = questionRepository.findById(id);


        if (question.isPresent()) {
            Optional<List<Option>> options = repository.findByQuestionId(id);

            if (options.isPresent()) {
                responseDto.setResult(options.get());
                responseDto.setWorked(true);
                responseDto.setMessage("Found Options of question number :" + id);
                return responseDto;

            } responseDto.setWorked(false);
            responseDto.setResult(null);
            responseDto.setMessage("no options were found for this question ");
            return responseDto;

        } responseDto.setWorked(false);
        responseDto.setResult(null);
        responseDto.setMessage("no question with this Id was found ");
        return responseDto;
    }






}
