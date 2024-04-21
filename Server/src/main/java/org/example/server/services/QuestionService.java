package org.example.server.services;

import lombok.RequiredArgsConstructor;
import org.example.server.Dtos.QuestionDto;
import org.example.server.models.Exam;
import org.example.server.models.Option;
import org.example.server.models.Question;
import org.example.server.models.ResponseDto;
import org.example.server.repositories.ExamRepository;
import org.example.server.repositories.OptionRepository;
import org.example.server.repositories.QuestionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepository repository;
    private final ExamRepository examRepository;
    private final OptionRepository optionRepository;
    private final ResponseDto responseDto = new ResponseDto();


    public ResponseDto getAllQuestions(){
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

    /**
     * add a question to an existing exam with it's options
     * @param questionDto
     * @return
     */
    public ResponseDto CreateQuestion(QuestionDto questionDto){
        // check if the exam is already created before adding the questions
        Optional<Exam> exam =  examRepository.findById(questionDto.getExam());

        // if there is no options provided that should be null by default,
        // although I think I should probably provide at least one option
        //nah keep it this way we have more than one type of questions

        List<Option> options = null;
        //save the options ofc
        //and save them into the question itself

        if (exam.isPresent()){
            if (questionDto.getOptions() !=null){
                options  = optionRepository.saveAll(questionDto.getOptions());

            }
            responseDto.setResult(repository.save(Question
                    .builder()
                    .type(questionDto.getType())
                    .exam(exam.get())
                            .answer(questionDto.getAnswer())
                            .description(questionDto.getDescription())
                            .difficultyLevel(questionDto.getDifficultyLevel())
                            .id(0L)
                            .score(questionDto.getScore())
                            .name(questionDto.getName())
                            .options(options)
                    .build()));
            responseDto.setWorked(true);
            responseDto.setMessage("Question Added Successfully ");
            return responseDto;
        }
        responseDto.setResult("Exam isn't found ");
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
