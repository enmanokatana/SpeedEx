package org.example.server.services;

import lombok.RequiredArgsConstructor;
import org.example.server.Dtos.RequestDto;
import org.example.server.Dtos.UserDto;
import org.example.server.models.ResponseDto;
import org.example.server.models.User;
import org.example.server.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService
{
    private final UserRepository userRepository;
    private final ImageService imageService;
    private RequestDto requestDto =new RequestDto();
    private ResponseDto responseDto = new ResponseDto();


    public ResponseDto GetListOfUsersByIds(List<Integer> ids){
        var users =  userRepository.findAllById(ids);
        List<UserDto> result = new ArrayList<>();
        for (User user:users){
            var us = UserDto.builder()
                    .email(user.getEmail())
                    .role(user.getRole())
                    .firstname(user.getFirstname())
                    .lastname(user.getLastname())
                    .profileImg(user.getProfileImg())
                    .build();
            result.add(us);
        }

        responseDto.setResult(result);
        responseDto.setMessage("users ");
        responseDto.setWorked(true);
        return responseDto;
    }

    public ResponseDto GetUserDtoById(Integer id){
        var user = userRepository.findById(id);
        if (user.isPresent()){
            var us = UserDto.builder()
                    .email(user.get().getEmail())
                    .role(user.get().getRole())
                    .firstname(user.get().getFirstname())
                    .lastname(user.get().getLastname())
                    .profileImg(user.get().getProfileImg())
                    .build();
            responseDto.setResult(us);
            responseDto.setMessage("Found User");
            responseDto.setWorked(true);
            return responseDto;
        }
        responseDto.setWorked(false);
        responseDto.setResult(null);
        responseDto.setMessage("User Was not found ");
        return responseDto;


    }
    public ResponseDto GetUserById(Integer id){
        var user = userRepository.findById(id);
        if (user.isPresent()){
            responseDto.setResult(user.get());
            responseDto.setMessage("Found User");
            responseDto.setWorked(true);
            return responseDto;
        }
        responseDto.setWorked(false);
        responseDto.setResult(null);
        responseDto.setMessage("User Was not found ");
        return responseDto;

    }
    public String saveImage(MultipartFile multipartFile , Integer id){
        try{
            String image =  imageService.upload(multipartFile);
            if (image != null){
                image = image.replace("<bucket-name>" ,"library-b3d3f.appspot.com" );
            }
            Optional<User> user= userRepository.findById(id);
            if (user.isPresent()){
                user.get().setProfileImg(image);
                userRepository.save(user.get());
                return image;
            }else{
                System.out.println("User Doesn't Exist DOESNT EXIST");
                return null;
            }


        }catch (Exception e){
            System.out.println(e);
            return null;
        }
    }

    public ResponseDto GetUserByEmail(String email){
        var user = userRepository.findByEmail(email);
        if (user.isPresent()){
            responseDto.setResult(user.get());
            responseDto.setWorked(true);
            responseDto.setMessage("Found The User");
            return  responseDto;

        }
        responseDto.setResult(null);
        responseDto.setWorked(false);

        responseDto.setMessage("Couldn't find USER or USER doesn't Exist ");
        return  responseDto;
    }

    public ResponseDto GetUserDtoByEmail(String email){
        var user = userRepository.findByEmail(email);

        if (user.isPresent()){
            responseDto.setResult(UserDto.builder()
                            .id(user.get().getId())
                            .profileImg(user.get().getProfileImg())
                            .email(user.get().getEmail())
                            .lastname(user.get().getLastname())
                            .firstname(user.get().getFirstname())
                            .role(user.get().getRole())
                    .build());
            responseDto.setWorked(true);
            responseDto.setMessage("Found The User");
            return  responseDto;

        }
        responseDto.setResult(null);
        responseDto.setWorked(false);

        responseDto.setMessage("Couldn't find USER or USER doesn't Exist ");
        return  responseDto;
    }

    }





