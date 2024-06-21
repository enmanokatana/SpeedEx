package org.example.server.mappers;

import org.example.server.Dtos.OptionDto;
import org.example.server.models.ExamGroup;
import org.example.server.models.Option;

import java.util.function.Function;

public class OptionDtoMapper implements Function<Option, OptionDto> {

    @Override
    public OptionDto apply(Option option) {
        return OptionDto.builder()
                .value(option.getValue())
                .isCorrect(option.isCorrect())
                .question(option.getQuestion().getId())
                .build();
    }


}
