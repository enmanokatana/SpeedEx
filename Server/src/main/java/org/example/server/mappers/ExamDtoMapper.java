package org.example.server.mappers;

import org.example.server.Dtos.ExamDto;
import org.example.server.models.Exam;

import java.util.function.Function;

public class ExamDtoMapper implements Function<Exam, ExamDto> {
    @Override
    public ExamDto apply(Exam exam) {
        return ExamDto.builder()
                .name(exam.getName())
                .timer(exam.getTimer())
                .passingScore(exam.getPassingScore())
                .description(exam.getDescription())
                .randomizeQuestions(exam.isRandomizeQuestions())
                .isPublic(exam.isPublic())
                .passed(exam.getPassed())
                .difficultyLevel(exam.getDifficultyLevel())
                .questions(exam.getQuestions())
                .user(exam.getUser().getId())
                .workspace(exam.getWorkspace().getId())
                .student(exam.getStudent().getId())
                .ExamGroup(exam.getExamGroup().getId())
                .passingDate(exam.getPassingDate())
                .result(exam.getResult())
                .build();
    }
}
