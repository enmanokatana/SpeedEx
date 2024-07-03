package org.example.server.controllers;

import lombok.RequiredArgsConstructor;
import org.example.server.Dtos.WorkSpaceDto;
import org.example.server.models.ResponseDto;
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
    @Deprecated
    public ResponseDto addUserToWorkSpaceByEmail(
        @PathVariable String email,
        @PathVariable Long id
    ){
        return workSpaceService.addUserToWorkspaceByEmail(email,id);

    }

    @PutMapping("/{id}/adduser/{userId}")
    public ResponseDto addUserToWorkSpaceByEmail(
            @PathVariable Integer userId,
            @PathVariable Long id
    ){
        return workSpaceService.addUserToWorkSpaceById(userId,id);

    }

    @DeleteMapping("/{id}/user/{userid}")
    public ResponseDto removeUserFromWorkSpace(
            @PathVariable Long id,
            @PathVariable Integer userid
    ){
        return workSpaceService.RemoveUserFromWorkSpace(userid,id);
    }


    /**
     *retreived by the Admin
     *
     */

    @GetMapping("/{id}/exams")
    public ResponseDto getWorkSpaceExams(
            @PathVariable Long id
    ){
        return workSpaceService.GetWorkSpaceExams(id);
    }

    /**
     * For the User to get his own exams from a workspace
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}/exams/user/{userId}")
    public ResponseDto getWorkSpaceExamsUser(
            @PathVariable Long id,
            @PathVariable Integer userId
    ){
        return workSpaceService.GetWorkSpaceExamsForAUser(id,userId);
    }
    /**
     * For the Admin to get his own prototypes exams from a workspace
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}/exams/admin/{userId}")
    public ResponseDto getWorkSpaceExamsAdmin(
            @PathVariable Long id,
            @PathVariable Integer userId
    ){
        return workSpaceService.GetWorkSpaceExamsForAnAdmin(id,userId);
    }
    @GetMapping("/dto/user/{id}")
    public ResponseDto getUserWorkSpaceDto(
            @PathVariable Long id
    ){
        return workSpaceService.getWorkSpaceDto(id);
    }
    @GetMapping("/image/{id}")
    public ResponseDto getImage(
            @PathVariable Long id
    ){
        return workSpaceService.getWorkSpaceImage(id);
    }
    @GetMapping("/name/{id}")
    public ResponseDto getName(
            @PathVariable Long id
    ){
        return workSpaceService.getWorkSpaceName(id);
    }

    @PostMapping("join/{id}")
    public ResponseDto joinByCode(
            @PathVariable Integer id ,
            @RequestParam String code
    ){
        return workSpaceService.joinUserToWorkSpaceByCode(id,code);
    }

}
