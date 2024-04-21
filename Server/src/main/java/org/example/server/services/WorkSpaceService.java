package org.example.server.services;

import lombok.RequiredArgsConstructor;
import org.example.server.Dtos.WorkSpaceDto;
import org.example.server.models.ResponseDto;
import org.example.server.models.User;
import org.example.server.models.Workspace;
import org.example.server.repositories.UserRepository;
import org.example.server.repositories.WorkspaceRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WorkSpaceService {

    private final WorkspaceRepository repository;
    private final UserRepository userRepository;
    private ResponseDto responseDto = new ResponseDto();


    public ResponseDto getWorkSpaceById(Long id){
        var ws = repository.findById(id);
        if (ws.isPresent()){
            responseDto.setResult(ws.get().getAdmin());
            responseDto.setMessage("Found Ws ");
            responseDto.setWorked(true);
            return responseDto;
        }
        responseDto.setMessage("Couldn't find this workspace");
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
        responseDto.setMessage("this admin doesn't exist");
        return responseDto;

    }

}
