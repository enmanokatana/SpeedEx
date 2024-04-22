import {Component, OnInit} from '@angular/core';
import {HeaderComponent} from "../../../core/componenets/header/header.component";
import {ActivatedRoute, Router, RouterLink} from "@angular/router";
import {ReactiveFormsModule} from "@angular/forms";
import {UserService} from "../../../core/services/User/user.service";
import {WorkspaceService} from "../../../core/services/workspace/workspace.service";
import {NgForOf, NgIf} from "@angular/common";

@Component({
  selector: 'app-workspace',
  standalone: true,
  imports: [
    HeaderComponent,
    RouterLink,
    ReactiveFormsModule,
    NgForOf,
    NgIf
  ],
  templateUrl: './workspace.component.html',
  styleUrl: './workspace.component.css'
})
export class WorkspaceComponent implements OnInit{

  constructor(private userService:UserService,
              private workspaceService:WorkspaceService,
              private route:ActivatedRoute,
              private router:Router) {
  }
  WorkSpaceAdmin!:any;
  WorkSpaceUsers:any[] = [];
  id:any;


  ngOnInit() {
    this.id = this.route.snapshot.paramMap.get('id')
    this.ongGetWorkSpaceUsers(this.id);
    this.onGetAdmin();

  }



  ongGetWorkSpaceUsers(id:any){
  this.workspaceService.getWorkSpaceUsersIds(id).subscribe({
    next:(response)=>{
      this.onLoadUsers(response.result);
    },
    error:(e)=>{
      console.log(e)
    },
    complete:()=>{
      console.log(this.WorkSpaceUsers);
    }
  });
  }
  onLoadUsers(ids:any){
    for (let id of ids) {
      this.userService.getUserDto(id).subscribe({
        next:(response)=>{
          this.WorkSpaceUsers.push(response.result);

        }
      })
    }
  }
  onGetAdmin(){
    this.workspaceService.getWorkSpaceAdmin(this.id).subscribe({
      next:(response)=>{

        this.WorkSpaceAdmin = response.result;
      },
      error:(e)=>{
        console.log(e);
      },
      complete:()=>{

      }
    })
  }


}
