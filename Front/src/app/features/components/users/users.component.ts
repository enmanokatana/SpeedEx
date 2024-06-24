import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators} from "@angular/forms";
import {NgForOf, NgIf} from "@angular/common";
import {WorkspaceService} from "../../../core/services/workspace/workspace.service";
import {ActivatedRoute, Router} from "@angular/router";
import {UserService} from "../../../core/services/User/user.service";

@Component({
  selector: 'app-users',
  standalone: true,
  imports: [
    FormsModule,
    NgForOf,
    NgIf,
    ReactiveFormsModule
  ],
  templateUrl: './users.component.html',
  styleUrl: './users.component.css'
})
export class UsersComponent implements OnInit{
  image:string = '';
  id:any;
  WorkSpaceAdmin: any;
  emailForm!:FormGroup;
  loading: boolean = true;
  user!: any;
  WorkSpaceUsers:any[] = [];
  user2:any = {
    id:0,
    name:'',
    firstname:'',
    lastname:''
  };


  constructor(private workspaceService:WorkspaceService,
              private route:ActivatedRoute,
              private router:Router,
              private builder: FormBuilder,
              private userService:UserService,) {
    console.log("test")
  }


  ngOnInit() {
    this.id = this.route.parent?.snapshot.params['id']

    this.emailForm = this.builder.group({
      email:this.builder.control('',Validators.email),
    })
    this.onGetAdmin();
    this.onGetWsImage();
    this.ongGetWorkSpaceUsers(this.id);


  }

  onGetWsImage(){
    this.workspaceService.getImage(this.id).subscribe({
      next:(response:any)=>{
        //console.log(response);
        this.image = response.result;
      },
      error:(e)=>{
        //console.log(e)
      },
      complete:()=>{

      }
    })
  }
  onGetAdmin(){
    this.workspaceService.getWorkSpaceAdmin(this.id).subscribe({
      next:(response)=>{

        this.WorkSpaceAdmin = response.result;
      },
      error:(e)=>{
        //console.log(e);
      },
      complete:()=>{

      }
    })
  }
  onFindUser():any{
    //console.log("email : ",this.emailForm.value.email)
    this.userService.getUserDtoByEmail(this.emailForm.value.email).subscribe({
      next:(response)=>{
        //console.log("User Response",response);
        this.user = response.result;
        this.emailForm.reset();
      },
      error:(e)=>{
        //console.log(e);
      },
      complete:()=>{
        this.loading=!this.loading;
        //console.log(this.loading);
      }
    })

  }
  onAddUser(){
    this.workspaceService.inviteUser(this.user.id,this.id).subscribe({
      next:(response)=>{
        //console.log(response);
      },
      error:(err)=>{
        //console.log(err);
      },
      complete:()=>{
      }
    })
  }
  onLoadUsers(ids:any){
    for (let id of ids) {
      this.userService.getUserDto(id).subscribe({
        next:(response)=>{
          //console.log("USERS: ,",response);
          this.user2.id = id;
          this.user2.lastname = response.result.lastname;
          this.WorkSpaceUsers.push(response.result);

        }
      })
    }
  }
  ongGetWorkSpaceUsers(id:any){
    this.workspaceService.getWorkSpaceUsersIds(id).subscribe({
      next:(response)=>{
        this.onLoadUsers(response.result);
      },
      error:(e)=>{
        //console.log(e)
      },
      complete:()=>{
        //console.log(this.WorkSpaceUsers);
      }
    });
  }
  onsetUser(user:any){
    this.user2.id=user.id;
    this.user2.firstname=user.firstname;
    this.user2.lastname=user.lastname;
    this.user2.email=user.email;

  }

  onRemoveUser(id:any){
    this.workspaceService.RemoveUserFromWs(id,this.id).subscribe({
      next:(response)=>{
        //console.log(response);
      },
      error:(err)=>{
        //console.log(err);
      },
      complete:()=>{
        //console.log("removed user with id " , id);

      }
    })

  }

  protected readonly localStorage = localStorage;
}
