package org.example.server.controllers;

import lombok.RequiredArgsConstructor;
import org.example.server.Dtos.WorkSpaceDto;
import org.example.server.models.ResponseDto;
import org.example.server.models.Workspace;
import org.example.server.services.WorkSpaceService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/Workspace")
@RequiredArgsConstructor
public class WorkspaceController {
    private final WorkSpaceService workSpaceService;


    @GetMapping("/{id}")
    public ResponseDto getWorkspaceById(
            @PathVariable Long id
    ){
        return  workSpaceService.getWorkSpaceById(id);
    }

    @GetMapping("/user/{id}")
    public ResponseDto getWorkspacesByAdmin(
            @PathVariable Integer id

    ){
        return workSpaceService.getWorkspaceByAdminId(id);
    }

    @PostMapping
    public ResponseDto createWorkspace(
            @RequestBody WorkSpaceDto workSpaceDto
            ){
        return workSpaceService.createWorkspace(workSpaceDto);
    }
    @GetMapping("/users/{id}")
    public ResponseDto GetUserFormWorkSpace(
            @PathVariable Long id
    )
    {
        return workSpaceService.getUsersFromWorkSpace(id);
    }
    @GetMapping("/admin/{id}")
    public ResponseDto GetAdminFromWorkSpace(
        @PathVariable Long id
    ){
        return workSpaceService.getWorkSpaceAdmin(id);
    }

    @PostMapping("/{id}/adduser/email/{email}")
    public ResponseDto addUserToWorkSpaceByEmail(
        @PathVariable String email,
        @PathVariable Long id
    ){
        return workSpaceService.addUserToWorkspaceByEmail(email,id);

    }

    @PostMapping("/{id}/adduser/{userId}")
    public ResponseDto addUserToWorkSpaceByEmail(
            @PathVariable Integer userId,
            @PathVariable Long id
    ){
        return workSpaceService.addUserToWorkSpaceById(userId,id);

    }
}
