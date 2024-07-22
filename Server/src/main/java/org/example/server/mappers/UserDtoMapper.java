package org.example.server.mappers;

import org.example.server.Dtos.ExamDto;
import org.example.server.Dtos.UserDto;
import org.example.server.models.Exam;
import org.example.server.models.User;

import java.util.function.Function;

public class UserDtoMapper implements Function<User, UserDto> {
    @Override
    public UserDto apply(User user) {
        return  UserDto.builder()
                .id(user.getId())
                .role(user.getRole())
                .email(user.getEmail())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .profileImg(user.getProfileImg())


                .build();
    }
}
