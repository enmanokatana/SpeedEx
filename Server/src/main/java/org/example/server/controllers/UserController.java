package org.example.server.controllers;

import lombok.RequiredArgsConstructor;
import org.example.server.models.ResponseDto;
import org.example.server.services.UserService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/User")
@RequiredArgsConstructor
public class UserController
{
    private final UserService userService;


    @GetMapping("/{id}")
    public ResponseDto GetUserById(
            @PathVariable Integer id

    ){
        return userService.GetUserById(id);

    }
    @PostMapping("/img/{id}")
    public String uploadImg(
            @PathVariable Integer id,
            @RequestParam("file") MultipartFile file
    ){
        return  userService.saveImage(file,id);
    }



}
