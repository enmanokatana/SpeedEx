import {Component, OnInit} from '@angular/core';
import {NgForOf, NgIf} from "@angular/common";
import {StoreService} from "../../../core/services/store/store.service";
import {AuthService} from "../../../core/services/Auth/auth.service";
import {WorkspaceService} from "../../../core/services/workspace/workspace.service";
import {UserService} from "../../../core/services/User/user.service";
import {RouterLink} from "@angular/router";

@Component({
  selector: 'app-main',
  standalone: true,
    imports: [
        NgForOf,
        RouterLink,
        NgIf
    ],
  templateUrl: './main.component.html',
  styleUrl: './main.component.css'
})
export class MainComponent implements OnInit{
  loggeduser:any = '';
  workspaces:any[]=[];
  userWorkspaces:any[]= [];
  loading: boolean=true;

  constructor(private store:StoreService,
              private service:AuthService,
              private workspaceService:WorkspaceService,
              private userService:UserService) {
  }
  ngOnInit() {
    this.loggeduser = this.store.isLogged();
    //console.log.log("Id here : ",localStorage.getItem('id'));
    console.log("Date : ",Date.now());

    if (localStorage.getItem('role')==="USER"){
      this.onGetWorkspacesUser();
    }else {
      this.onGetMyWorkspaces();
    }
    setTimeout(() => {

      this.loading = !this.loading;
    },3000);
  }

  onGetWorkspacesUser(){
    this.userService.getUserWorkSpacesIds(localStorage.getItem('id')).subscribe({
      next:(response)=>{
        //console.log.log(response.result);
        for (let i in response.result){
          //console.log.log(response.result[i]);
          this.workspaceService.getWorkSpaceDto(response.result[i]).subscribe({
            next:(res)=>{
              //console.log.log("dto :",res);
              this.userWorkspaces.push(res.result);

            }
          })
        }

      },
      error:(error)=>{
        //console.log.log(error);
      },
      complete:()=>{
        //console.log.log("User WorkSPaces",this.userWorkspaces);

      }
    })


  }
  onGetMyWorkspaces() {
    this.workspaceService.getWorkSpaces(localStorage.getItem('id')).subscribe({
      next:(response)=>{
        //console.log.log(response);
        this.workspaces=response.result;


      },
      error:(e)=>{
        //console.log.log(e);

      },
      complete:()=>{
        //console.log.log("Completed getting WSS")
      }
    })
  }

}
