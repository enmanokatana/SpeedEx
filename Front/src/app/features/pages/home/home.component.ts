import { Component, OnInit } from '@angular/core';
import {RouterLink, RouterOutlet} from '@angular/router';
import { StoreService } from '../../../core/services/store/store.service';
import { AuthService } from '../../../core/services/Auth/auth.service';
import {HeaderComponent} from "../../../core/componenets/header/header.component";

import {WorkspaceService} from "../../../core/services/workspace/workspace.service";
import {NgForOf, NgIf} from "@angular/common";
import {UserService} from "../../../core/services/User/user.service";
import {ChatboxComponent} from "../../components/chatbox/chatbox.component";
@Component({
  selector: 'app-home',
  standalone: true,
  imports: [RouterLink, HeaderComponent, NgForOf, NgIf, RouterOutlet, ChatboxComponent],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent implements OnInit {

  loading:boolean = true;

  constructor(
    ) {

  }

  ngOnInit(): void {



    setTimeout(() => {

      this.loading = !this.loading;
       },1000);


  }







  protected readonly localStorage = localStorage;
}
