package org.example.server.controllers;

import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import org.example.server.models.Exam;
import org.example.server.models.ResponseDto;
import org.example.server.services.UserService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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

    @GetMapping("/usersdtos")
    public ResponseDto GetUsersByIds(
            @RequestBody List<Integer> users
            ){
        return  userService.GetListOfUsersByIds(users);
    }
    @GetMapping("/userdto/{id}")
    public ResponseDto GetUserDtoById(
            @PathVariable Integer id

    ){
        return userService.GetUserDtoById(id);

    }
    @GetMapping("/email/{email}")// needs to be fixed somehow ?
    public ResponseDto GetUserByEmail(
        @PathVariable String email
    ){
        return userService.GetUserByEmail(email);

    }
    @GetMapping("/emaildto/{email}")// needs to be fixed somehow ?
    public ResponseDto GetUserDtoByEmail(
            @PathVariable String email
    ){
        return userService.GetUserDtoByEmail(email);

    }



}
