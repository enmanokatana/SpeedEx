package org.example.server.services;

import lombok.RequiredArgsConstructor;
import org.example.server.Dtos.ExamDto;
import org.example.server.Dtos.ExamGroupDto;
import org.example.server.models.Exam;
import org.example.server.models.ExamGroup;
import org.example.server.models.ResponseDto;
import org.example.server.models.User;
import org.example.server.repositories.ExamGroupRepository;
import org.example.server.repositories.ExamRepository;
import org.example.server.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExamGroupService {
    private final ExamGroupRepository repository;
    private final UserRepository userRepository;
    private final ExamRepository examRepository;
    private final ResponseDto responseDto = new ResponseDto();



    public ResponseDto CreateExamGroup(ExamGroupDto dto){

        List<Exam> exams = new ArrayList<>();
        Optional<User> admin = userRepository.findById(dto.getAdmin());
        if (admin.isEmpty()){
            responseDto.setMessage("ADMIN DOESNT EXIST ");
            responseDto.setWorked(false);
            responseDto.setResult(null);
            return responseDto;
        }
        for (Long examId : dto.getExams() ){
            Optional<Exam> exam = examRepository.findById(examId);
            if (exam.isEmpty()){
                // We'll deal with you later
            }else {
                exams.add(exam.get());
            }
        }
        ExamGroup result= repository.save(ExamGroup.builder()
                        .id(0)
                        .exams(exams)
                        .build());


        responseDto.setMessage("Successfully Added the ExamGroup");
        responseDto.setWorked(true);
        responseDto.setResult(result);
        return responseDto;
    }

    public ResponseDto getExamsByGroupId(Integer id){
        Optional<ExamGroup> examGroup = repository.findById(id);
        if (examGroup.isEmpty()){
            responseDto.setMessage("ExamGroup DOESNT EXIST ");
            responseDto.setWorked(false);
            responseDto.setResult(null);
            return responseDto;
        }
        List<ExamDto> examDtoList = new ArrayList<>();
        if (examGroup.get().getExams() == null){
            responseDto.setMessage("Error no exams has been found ");
            responseDto.setWorked(false);
            responseDto.setResult(null);
            return responseDto;
        }


        for (Exam exam : examGroup.get().getExams()){
            if (exam.getStudent() !=null){
            examDtoList.add(ExamDto.builder()
                            .id(exam.getId())
                            .name(exam.getName())
                            .description(exam.getDescription())
                            .timer(exam.getTimer())
                            .difficultyLevel(exam.getDifficultyLevel())
                            .student(exam.getStudent().getId())
                            .passed(exam.getPassed())
                            .result(exam.getResult())
                            .passingScore(exam.getPassingScore())
                    .build());
            }
        }
        responseDto.setResult(examDtoList);
        responseDto.setMessage("got ExamGroup exams");
        responseDto.setWorked(true);
        return responseDto;


    }








}
