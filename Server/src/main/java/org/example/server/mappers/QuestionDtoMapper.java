package org.example.server.mappers;

import org.example.server.Dtos.OptionDto;
import org.example.server.Dtos.QuestionDto;
import org.example.server.models.Option;
import org.example.server.models.Question;

import java.util.function.Function;

public class QuestionDtoMapper implements Function<Question, QuestionDto> {


    @Override
    public QuestionDto apply(Question question) {
        return QuestionDto.builder()
                .name(question.getName())
                .description(question.getDescription())
                .type(question.getType())
                .answer(question.getAnswer())
                .timer(question.getTimer())
                .userAnswer(question.getUserAnswer())
                .userAnswer(question.getUserAnswer())
                .score(question.getScore())
                .exam(question.getExam().getId())
                .difficultyLevel(question.getDifficultyLevel())
                .options(question.getOptions())
                .build();
    }
}
