import { Component, OnInit } from '@angular/core';
import { RouterLink } from '@angular/router';
import { StoreService } from '../../../core/services/store/store.service';
import { AuthService } from '../../../core/services/Auth/auth.service';
import {HeaderComponent} from "../../../core/componenets/header/header.component";
import {BrnCommandComponent} from "@spartan-ng/ui-command-brain";
import {HlmCommandInputWrapperComponent} from "@spartan-ng/ui-command-helm";
import {WorkspaceService} from "../../../core/services/workspace/workspace.service";
import {NgForOf} from "@angular/common";

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [RouterLink, HeaderComponent, BrnCommandComponent, HlmCommandInputWrapperComponent, NgForOf],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent implements OnInit {
  loggeduser:any = '';
  workspaces:any[]=[];

  constructor(private store:StoreService,
    private service:AuthService,
              private workspaceService:WorkspaceService
    ) {

  }

  ngOnInit(): void {
        this.loggeduser = this.store.isLogged();
        console.log(this.loggeduser);
        this.store.getUser();
        this.onGetMyWorkspaces();
  }
  OnLogout(){
      this.service.logout();
  }
  onGetMyWorkspaces() {
    this.workspaceService.getWorkSpaces(localStorage.getItem('id')).subscribe({
      next:(response)=>{
        console.log(response);
        this.workspaces=response.result;


      },
      error:(e)=>{
        console.log(e);

      },
      complete:()=>{
        console.log("Completed getting WSS")}
    })
  }

}
