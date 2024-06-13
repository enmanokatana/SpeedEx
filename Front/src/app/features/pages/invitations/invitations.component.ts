import {Component, OnInit} from '@angular/core';
import {WorkspaceService} from "../../../core/services/workspace/workspace.service";
import {NgForOf} from "@angular/common";
import {HeaderComponent} from "../../../core/componenets/header/header.component";

@Component({
  selector: 'app-invitations',
  standalone: true,
  imports: [
    NgForOf,
    HeaderComponent
  ],
  templateUrl: './invitations.component.html',
  styleUrl: './invitations.component.css'
})
export class InvitationsComponent implements  OnInit{

  invitations:any[] = [];
  invitationswithnames:any[]=[];
  constructor(private  workspaceService:WorkspaceService) {
  }
  ngOnInit() {
    this.onGetInvites();

  }


  onGetInvites(){
    this.workspaceService.getAllInvites(localStorage.getItem('id')).subscribe({
      next:(res:any)=>{
        console.log(res);
        this.invitations = res.result;
        this.assignNames(this.invitations);

      }
    })

  }
  onAcceptInvitation(inv:any){
    this.workspaceService.AcceptInvite(inv).subscribe({
      next:(res:any)=> {
        console.log(res);
        this.invitations = this.invitations.filter(i => i.id !== inv.id );
      },
      error:(e)=>{
        console.log(e);
      },
    })
  }
  onDeclineInvitation(inv:any){
    this.workspaceService.DeclineInvitation(inv).subscribe({
      next:(res:any)=> {
        console.log(res);
        this.invitations = this.invitations.filter(i => i.id !== inv.id );
      },
      error:(e)=>{
        console.log(e);
      },
    })
  }
  assignNames(invitations:any){
    for (let inv of invitations) {
      console.log(inv)
      let name ;
      this.workspaceService.getName(inv.workspaceId).subscribe({
        next: (res: any) => {
          console.log("naem", res.result)
          const obj: any = {
            name: res.result,
            id: inv.id,
            workspaceId: inv.workspaceId,
            userId: inv.userId,
            result: inv.result

          };
          this.invitationswithnames.push(obj);
        },
        error: (e) => {
          console.log(e);
        },
      });


    }
    console.log(this.invitationswithnames);

    }




}
