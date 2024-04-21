package org.example.server.services;

import lombok.RequiredArgsConstructor;
import org.example.server.models.ResponseDto;
import org.example.server.models.User;
import org.example.server.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService
{
    private final UserRepository userRepository;
    private final ImageService imageService;

    private ResponseDto responseDto = new ResponseDto();
    public ResponseDto GetUserById(Integer id){
        var user = userRepository.findById(id);
        if (user.isPresent()){
            responseDto.setResult(user.get());
            responseDto.setMessage("Found User");
            responseDto.setWorked(true);
            return responseDto;
        }
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

    }





