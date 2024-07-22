package org.example.server.Dtos;

import org.springframework.web.multipart.MultipartFile;

public record UpdateProfileRequest(UserDto userDto, MultipartFile file) {
}
