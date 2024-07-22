package org.example.server.controllers;

import lombok.extern.slf4j.Slf4j;
import org.example.server.Dtos.UpdateProfileRequest;
import org.example.server.Dtos.UserDto;
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
@Slf4j
public class UserController
{
    private final UserService userService;


    @GetMapping("/onlineUsers")
    public List<String> getOnlineUsers() {
        return userService.getOnlineUsers();
    }

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

    @GetMapping("/workspacesids/{id}")
    public ResponseDto GetUserWorkSpaceIds(
            @PathVariable Integer id
    ){
        return userService.GetUsersWorkSpaces(id);
    }

    @PutMapping("updateProfile/{id}")
    public ResponseDto updateProfile(

            @RequestBody UserDto userDto,
            @PathVariable Integer id
            ){
        return userService.UpdateProfile(userDto,id);
    }


    @PostMapping("UpdateProfilePicture/{id}")
    public String updateProfilePicture(
            @RequestBody MultipartFile file,
            @PathVariable Integer id){
        log.info("the FILE :"+file.toString());
        return userService.saveImage(file,id);
    }





   /* @GetMapping("/workspacesdtos")
    public ResponseDto GetWorkSpacesDtos(
            @RequestBody List<Long> ids
    ){
        return userService.GetUserWorkSpacesDtos(ids);
    }*/
}