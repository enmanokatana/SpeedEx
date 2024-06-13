package org.example.server.controllers;

import lombok.RequiredArgsConstructor;
import org.example.server.models.Invitation;
import org.example.server.models.ResponseDto;
import org.example.server.services.InvitationService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/Invitations")
@RequiredArgsConstructor
public class InvitationController {
    private final InvitationService invitationService;
    @PostMapping("/user/{userId}/Ws/{wsId}")
    ResponseDto InviteUser(
            @PathVariable Integer userId
            ,@PathVariable Long wsId
    ){
        return invitationService.InviteUser(userId,wsId);

    }





    @PostMapping("/accept")
    public ResponseDto AcceptInv(
            @RequestBody Invitation invitation
            ){
     return    invitationService.AcceptInvitation(invitation);
    }
    @PostMapping("/decline")
    public ResponseDto DeclineInv(
            @RequestBody Invitation invitation
            ){
        return invitationService.DeclineInvitation(invitation);
    }
    @GetMapping("/{userId}")
    public ResponseDto getAllInvByUserId(
            @PathVariable Integer userId
    ){
        return invitationService.GetAllInvitations(userId);
    }
}
