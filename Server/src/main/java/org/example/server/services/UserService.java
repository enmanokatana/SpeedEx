package org.example.server.services;

import lombok.RequiredArgsConstructor;
import org.example.server.Dtos.ExamDto;
import org.example.server.Dtos.RequestDto;
import org.example.server.Dtos.UserDto;
import org.example.server.Dtos.WorkSpaceDto;
import org.example.server.exceptions.UserDoesNotExistException;
import org.example.server.models.Exam;
import org.example.server.models.ResponseDto;
import org.example.server.models.User;
import org.example.server.models.Workspace;
import org.example.server.repositories.UserRepository;
import org.example.server.repositories.WorkspaceRepository;
import org.example.server.utils.TokenRegistry;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService
{
    private final UserRepository userRepository;
    private final ImageService imageService;
    private final WorkspaceRepository workspaceRepository;
    private final ResponseDto responseDto = new ResponseDto();

    private final TokenRegistry tokenRegistry;



    public Boolean IsUserOnline(Integer id) throws UserDoesNotExistException {
        var user = userRepository.findById(id).orElseThrow(()->new UserDoesNotExistException("user Doesn't Exist") );
        return getOnlineUsers().contains(user.getEmail());
    }

    public List<String> getOnlineUsers(){
        return tokenRegistry.getActiveTokens().values().stream().distinct().collect(Collectors.toList());
    }

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

    public List<WorkSpaceDto> ChangeWorkspaceToWorkSpaceDTO(List<Workspace> workspace){
        List<WorkSpaceDto> workSpaceDtos = new ArrayList<>();
        for (Workspace workspace1: workspace){
           workSpaceDtos.add( WorkSpaceDto.builder()
                   .id(workspace1.getId())
                   .build());
        }

        return workSpaceDtos;
    }

    public ResponseDto GetUsersWorkSpaces(Integer id){
        var user = userRepository.findById(id);
        if (user.isPresent()){
            var workspaces = ChangeWorkspaceToWorkSpaceDTO(user.get().getWorkspaces());
            List<Long> workspacesids = new ArrayList<>();
            for (WorkSpaceDto workspace:workspaces){
                workspacesids.add(workspace.getId());
            }
            responseDto.setResult(workspacesids);
            responseDto.setWorked(true);
            responseDto.setMessage("got workspacesIds");
            return responseDto;

        }
        responseDto.setWorked(false);
        responseDto.setResult(null);
        responseDto.setMessage("User Was not found ");
        return responseDto;

    }


    public ResponseDto GetUserWorkSpacesDtos(List<Long> ids){
        var workspaces = workspaceRepository.findAllById(ids);
        List<WorkSpaceDto> workSpaceDtos = new ArrayList<>();
        for (Workspace workspace:workspaces){
            workSpaceDtos.add(WorkSpaceDto.builder()
                            .name(workspace.getName())
                            .description(workspace.getDescription())
                            .id(workspace.getId())
                    .build());

        }
        responseDto.setResult(workSpaceDtos);
        responseDto.setWorked(true);
        responseDto.setMessage("Found WorkSpaces Dtos");
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
                    .id(user.get().getId())
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
                var usertest = userRepository.save(user.get());
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

    public List<ExamDto> getPasssingExams(Integer id){
        var user = userRepository.findById(id);
        if (user.isPresent()){
            List<ExamDto> exams = new ArrayList<>();
            for (Exam exam :user.get().getExams()){
                exams.add(ExamDto.builder()
                        .id(exam.getId())
                        .build());
            }
            return exams;
        }
        return null;
    }

    public ResponseDto UpdateProfile(UserDto userDto , MultipartFile image){
        var user = userRepository.findById(userDto.getId());
        if (user.isEmpty()){
            responseDto.setResult(null);
            responseDto.setWorked(false);
            responseDto.setMessage("Couldn't find USER or USER doesn't Exist ");
            return responseDto;
        }



        if(image != null){
            var result = saveImage(image , userDto.getId());

            if (result != null){
                responseDto.setResult(null);
                responseDto.setWorked(false);
                responseDto.setMessage("Couldn't save  Image");
                return responseDto;
            }
        }
        user.get().setFirstname(userDto.getFirstname());
        user.get().setLastname(userDto.getLastname());
        responseDto.setResult(userRepository.save(user.get()));
        responseDto.setWorked(true);
        responseDto.setMessage("Updated profile with success");
        return responseDto;

    }

    }





