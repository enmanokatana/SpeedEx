package org.example.server.services;

import lombok.RequiredArgsConstructor;
import org.example.server.Dtos.ExamDto;
import org.example.server.models.Exam;
import org.example.server.models.Option;
import org.example.server.models.Question;
import org.example.server.models.ResponseDto;
import org.example.server.repositories.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExamService {
    private final ExamRepository repository;
    private final QuestionRepository questionRepository;
    private final OptionRepository optionRepository;
    private final UserRepository userRepository;
    private final WorkspaceRepository workspaceRepository;

    private ResponseDto responseDto =new ResponseDto();
    public ResponseDto createExam(ExamDto examDto){

        var user = userRepository.findById(examDto.getUser());
        var workspace = workspaceRepository.findById(examDto.getWorkspace());



if (workspace.isPresent()){
    if (user.isPresent()){
        List<Question> questions = null;
        if (
                examDto.getQuestions() != null
        ) {
            questions = examDto.getQuestions();
        }
        responseDto.setResult(repository.save(Exam.builder()
                .description(examDto.getDescription())
                .isPublic(examDto.isPublic())
                .randomizeQuestions(examDto.isRandomizeQuestions())
                .passingScore(examDto.getPassingScore())
                .createdOn(LocalDateTime.now())
                .timer(examDto.getTimer())
                .name(examDto.getName())
                .difficultyLevel(examDto.getDifficultyLevel())
                .id(0L)
                .questions(questions)
                .user(user.get())
                .workspace(workspace.get())
                .build()));

        responseDto.setMessage("saved exam Successfully ");
        responseDto.setWorked(true);
        assert questions != null;
        for (Question question : questions) {
            question.setExam((Exam) responseDto.getResult());

        }
        var res = questionRepository.saveAll(questions);

        for (Question obj:res){
            var ops = obj.getOptions();
            if (ops!=null){
                for (Option option:ops){
                    option.setQuestion(obj);
                }
                optionRepository.saveAll(ops);

            }
        }

        return responseDto;

    }
    responseDto.setWorked(false);
    responseDto.setResult(null);
    responseDto.setMessage("User doesn't Exist");
    return responseDto;
}

        responseDto.setWorked(false);
        responseDto.setResult(null);
        responseDto.setMessage("WorkSpace doesn't Exist");
        return responseDto;



    }

    public ResponseDto findExamsByUser(Integer id){
        var user = userRepository.findById(id);
        if (user.isPresent()){
            List<Exam> exams = repository.findByUser(user.get());
            responseDto.setResult(exams);
            responseDto.setMessage("Exams found");
            responseDto.setWorked(true);
            return responseDto;
        }
        responseDto.setWorked(false);
        responseDto.setResult(null);
        responseDto.setMessage("Couldn't find the user with the id  : " + id);
        return responseDto;

    }

    public ResponseDto findExamById(Long id){
        var exam =repository.findById(id);
        if (exam.isPresent()){
            responseDto.setResult(exam.get());
            responseDto.setMessage("Found Exam successfully ");
            responseDto.setWorked(true);
            return  responseDto;

        } responseDto.setWorked(false);
        responseDto.setResult(null);

        responseDto.setMessage("exam doesn't exist with the id : " + id);
        return  responseDto;

    }



    public ResponseDto deleteExam(Long id){
        var exam =repository.findById(id);
        if (exam.isPresent()){
            repository.delete(exam.get());
            responseDto.setResult(exam.get());
            responseDto.setMessage("Deleted Exam successfully ");
            responseDto.setWorked(true);
            return  responseDto;

        }
        responseDto.setWorked(false);
        responseDto.setResult(null);
        responseDto.setMessage("exam doesn't exist with the id : " + id);
        return  responseDto;
    }
}
