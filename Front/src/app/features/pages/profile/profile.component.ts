import {Component, OnInit} from '@angular/core';
import {UserService} from "../../../core/services/User/user.service";
import {StoreService} from "../../../core/services/store/store.service";
import {NgIf} from "@angular/common";
import {RouterLink} from "@angular/router";

@Component({
  selector: 'app-profile',
  standalone: true,
  imports: [
    NgIf,
    RouterLink
  ],
  templateUrl: './profile.component.html',
  styleUrl: './profile.component.css'
})
export class ProfileComponent implements OnInit{

  showMore:boolean = false;
  user:any={
    firstname : '',
    lastname:'',
    email:'',
    role:'',
    image:''
  }
  constructor(private userService : UserService
  ,private store:StoreService) {
  }

  ngOnInit(): void {
    this.onGetUser();

  }

  onGetUser(){
    this.userService.GetUser(localStorage.getItem('id')).subscribe({
      next:(response)=>{
        console.log("second Response",response);
        this.user = {
          firstname:response.result.firstname,
          lastname:response.result.lastname,
          email:response.result.email,
          role:response.result.role,
          image:localStorage.getItem('image')
        }

      },
      error:(error) => {
        console.error('Login error', error);
      },
      complete:() => {
      }
    })
  }
  onShowMore(){
    this.showMore=!this.showMore;
    console.log(this.showMore);
  }

  protected readonly console = console;
}
