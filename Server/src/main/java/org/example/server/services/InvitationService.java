package org.example.server.services;

import lombok.RequiredArgsConstructor;
import org.example.server.models.Invitation;
import org.example.server.models.ResponseDto;
import org.example.server.repositories.InvitationRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InvitationService {
    private final InvitationRepository repository;
    private final WorkSpaceService workSpaceService;
    private final ResponseDto responseDto = new ResponseDto();

    public ResponseDto InviteUser(Integer User , Long WorksSP){

        try{

            var ws = repository.save(Invitation.builder()
                    .Id(null)
                    .result(false)
                    .userId(User)
                    .workspaceId(WorksSP).build());
            responseDto.setMessage("Invitation sent ");
            responseDto.setWorked(true);
            responseDto.setResult(ws);
            return responseDto;

        }catch (Exception e){
            responseDto.setResult(null);
            responseDto.setWorked(false);
            responseDto.setMessage(e.getMessage());
            return responseDto;
        }

    }

    public ResponseDto AcceptInvitation(Invitation invitation){
        repository.delete(invitation);
        return workSpaceService.addUserToWorkSpaceById(invitation.getUserId(),invitation.getWorkspaceId());



    }
    public ResponseDto DeclineInvitation(Invitation invitation){

        repository.delete(invitation);
        responseDto.setResult(null);
        responseDto.setWorked(false);
        responseDto.setMessage("Declined");
        return responseDto;
    }
    public ResponseDto GetAllInvitations(Integer UserID){
        var invitations = repository.findAllByUserId(UserID);

         responseDto.setResult(invitations);
        responseDto.setWorked(true);
        responseDto.setMessage("Got all the invitations successfully ");


        return responseDto;
    }

}
