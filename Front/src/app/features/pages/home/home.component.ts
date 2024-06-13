import { Component, OnInit } from '@angular/core';
import { RouterLink } from '@angular/router';
import { StoreService } from '../../../core/services/store/store.service';
import { AuthService } from '../../../core/services/Auth/auth.service';
import {HeaderComponent} from "../../../core/componenets/header/header.component";

import {WorkspaceService} from "../../../core/services/workspace/workspace.service";
import {NgForOf, NgIf} from "@angular/common";
import {UserService} from "../../../core/services/User/user.service";

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [RouterLink, HeaderComponent,NgForOf, NgIf],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent implements OnInit {
  loggeduser:any = '';
  workspaces:any[]=[];
  userWorkspaces:any[]= [];
  loading:boolean = true;

  constructor(private store:StoreService,
    private service:AuthService,
              private workspaceService:WorkspaceService,
              private userService:UserService
    ) {

  }

  ngOnInit(): void {
        this.loggeduser = this.store.isLogged();
        //console.log.log("Id here : ",localStorage.getItem('id'));

        if (localStorage.getItem('role')==="USER"){
          this.onGetWorkspacesUser();
        }else {
          this.onGetMyWorkspaces();
        }
       setTimeout(() => {
      // Code to execute after the specified delay
      //console.log.log('Delayed code executed after 2000 milliseconds');
      this.loading = !this.loading;
       },2000);

  }
  OnLogout(){
      this.service.logout();
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





  protected readonly localStorage = localStorage;
}
