package org.example.server.services;

import lombok.RequiredArgsConstructor;
import org.example.server.Dtos.UserDto;
import org.example.server.Dtos.WorkSpaceDto;
import org.example.server.models.ResponseDto;
import org.example.server.models.User;
import org.example.server.models.Workspace;
import org.example.server.repositories.UserRepository;
import org.example.server.repositories.WorkspaceRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WorkSpaceService {

    private final WorkspaceRepository repository;
    private final UserRepository userRepository;
    private ResponseDto responseDto = new ResponseDto();




    public ResponseDto getWorkSpaceAdmin(Long id){
        var ws = repository.findById(id);
        if (ws.isPresent()){
            var user = ws.get().getAdmin();
            var admin = UserDto.builder()
                    .email(user.getEmail())
                    .role(user.getRole())
                    .firstname(user.getFirstname())
                    .lastname(user.getLastname())
                    .profileImg(user.getProfileImg())
                    .build();
            responseDto.setResult(admin);
            responseDto.setMessage("Found Ws Admin ");
            responseDto.setWorked(true);
            return responseDto;
        }
        responseDto.setWorked(false);
        responseDto.setResult(null);
        responseDto.setMessage("Couldn't find this workspace");
        return responseDto;
    }

    public ResponseDto getWorkSpaceById(Long id){
        var ws = repository.findById(id);
        if (ws.isPresent()){
            responseDto.setResult(ws.get().getAdmin());
            responseDto.setMessage("Found Ws Admin ");
            responseDto.setWorked(true);
            return responseDto;
        }
        responseDto.setWorked(false);
        responseDto.setResult(null);
        responseDto.setMessage("Couldn't find this workspace");
        return responseDto;

    }

    public ResponseDto addUserToWorkSpaceById(Integer id , Long Wid) {
        var user = userRepository.findById(id);
        var workspace = repository.findById(Wid);
        if (user.isPresent()){
            if (workspace.isPresent()){
                if (workspace.get().getUsers().contains(user.get())){
                    responseDto.setWorked(false);
                    responseDto.setResult(null);
                    responseDto.setMessage("User Already Exist in WorkSpace");
                    return responseDto;
                }
                workspace.get().getUsers().add(user.get());
                repository.save(workspace.get());
                responseDto.setWorked(true);
                responseDto.setMessage("Added User Successfully to WorkSpace");
                responseDto.setResult(null);
                return responseDto;
            }
            responseDto.setWorked(false);
            responseDto.setResult(null);
            responseDto.setMessage("WorkSpace Doesn't Exist");
            return responseDto;
        }
        responseDto.setWorked(false);
        responseDto.setResult(null);
        responseDto.setMessage("User doesn't exist");

        return responseDto;
    }

    public ResponseDto addUserToWorkspaceByEmail(String email ,Long Wid ){
        var user = userRepository.findByEmail(email);
        var workspace = repository.findById(Wid);
        if (user.isPresent()){
            if (workspace.isPresent()){
                if (workspace.get().getUsers().contains(user.get())){
                    responseDto.setWorked(false);
                    responseDto.setResult(null);
                    responseDto.setMessage("User Already Exist in WorkSpace");
                    return responseDto;
                }
                workspace.get().getUsers().add(user.get());
                repository.save(workspace.get());
                responseDto.setWorked(true);
                responseDto.setMessage("Added User Successfully to WorkSpace");
                responseDto.setResult(null);
                return responseDto;
            }
            responseDto.setWorked(false);
            responseDto.setResult(null);
            responseDto.setMessage("WorkSpace Doesn't Exist");
            return responseDto;
        }
        responseDto.setWorked(false);
        responseDto.setResult(null);
        responseDto.setMessage("User doesn't exist");

        return responseDto;
    }



    public ResponseDto createWorkspace(WorkSpaceDto workSpaceDto){

        Optional<User> admin = userRepository.findById(workSpaceDto.getAdmin());

        if (admin.isPresent()){

            var users = userRepository.findAllById(workSpaceDto.getUsers());

            if(users.isEmpty()){
                users = null;
            }

            responseDto.setResult(repository.save(Workspace.builder()
                    .admin(admin.get())
                            .name(workSpaceDto.getName())
                            .id(0L)
                            .description(workSpaceDto.getDescription())
                            .exams(null)
                            .users(users)
                    .build()));

            admin.get().getWorkspaces().add((Workspace) responseDto.getResult());

            if (!users.isEmpty()){
                for (User user:users){
                    user.getWorkspaces().add((Workspace) responseDto.getResult());
                }
            }
            responseDto.setWorked(true);
            responseDto.setMessage("Created Workspace Successfully");



            return  responseDto;
        }
        responseDto.setWorked(false);
        responseDto.setResult(null);
        responseDto.setMessage("Admin doesn't Exist");
        return responseDto;


    }

    public ResponseDto getWorkspaceByAdminId(Integer id){
        var admin = userRepository.findById(id);
        if (admin.isPresent()){
            var workspaces = repository.findByAdmin(admin.get());
            responseDto.setResult(workspaces);
            responseDto.setMessage("Found Wspaces");
            responseDto.setWorked(true);
            return responseDto;

        }
        responseDto.setWorked(false);
        responseDto.setResult(null);
        responseDto.setMessage("this admin doesn't exist");
        return responseDto;

    }

    public ResponseDto getUsersFromWorkSpace(Long id){

        var workspace = repository.findById(id);

        if (workspace.isPresent()){
            var users = workspace.get().getUsers();
            List<Integer> userIds = new ArrayList<>();
            for (User user:users){
                userIds.add(user.getId());
            }
            responseDto.setResult(userIds);
            responseDto.setMessage("FoundUsers that belong to this workspace");
            responseDto.setWorked(true);
            return responseDto;

        }
        responseDto.setWorked(false);
        responseDto.setResult(null);
        responseDto.setMessage("Workspace doesn't Exist");
        return  responseDto;



    }

}
