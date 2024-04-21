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

}
