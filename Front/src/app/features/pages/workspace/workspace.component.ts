import {Component, OnInit} from '@angular/core';
import {HeaderComponent} from "../../../core/componenets/header/header.component";
import {ActivatedRoute, NavigationEnd, Router, RouterLink} from "@angular/router";
import {FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators} from "@angular/forms";
import {UserService} from "../../../core/services/User/user.service";
import {WorkspaceService} from "../../../core/services/workspace/workspace.service";
import {NgForOf, NgIf} from "@angular/common";
import {RightClickDirective} from "../../../core/Directives/right-click.directive";
import {filter} from "rxjs";

@Component({
  selector: 'app-workspace',
  standalone: true,
  imports: [
    HeaderComponent,
    RouterLink,
    ReactiveFormsModule,
    NgForOf,
    NgIf,
    FormsModule,
    RightClickDirective,


  ],
  templateUrl: './workspace.component.html',
  styleUrl: './workspace.component.css'
})
export class WorkspaceComponent implements OnInit{

  constructor(private userService:UserService,
              private workspaceService:WorkspaceService,
              private route:ActivatedRoute,
              private router:Router,
              private builder: FormBuilder) {

  }


  email : string ="";
  WorkSpaceAdmin!:any;
  WorkSpaceUsers:any[] = [];
  id:any;
  exams:any[]=[];
  user!:any;
  user2:any = {
    id:0,
    name:'',
    firstname:'',
    lastname:''
  };
  loading:boolean = true;
workspaces:any;
  emailForm!:FormGroup;

  ngOnInit() {
    this.emailForm = this.builder.group({
      email:this.builder.control('',Validators.email),
    })
    this.id = this.route.snapshot.paramMap.get('id')
    this.ongGetWorkSpaceUsers(this.id);
    this.onGetAdmin();
    this.onGetExams(this.id);
    this.onGetMyWorkspaces();
    this.router.events.pipe(
      filter(event => event instanceof NavigationEnd)
    ).subscribe(() => {
      window.location.reload();
    });

  }

onTest( event : MouseEvent){
  console.log("helo")
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
  onGetMyWorkspaces() {
    this.workspaceService.getWorkSpaces(localStorage.getItem('id')).subscribe({
      next:(response)=>{
        console.log(response);
        console.log('wss',response);
        this.workspaces=response.result;


      },
      error:(e)=>{
        console.log(e);

      },
      complete:()=>{
        console.log("Completed getting WSS")}
    })
  }

  onLoadUsers(ids:any){
    for (let id of ids) {
      this.userService.getUserDto(id).subscribe({
        next:(response)=>{
          console.log("USERS: ,",response);
          this.user2.id = id;
          this.user2.lastname = response.result.lastname;
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

  onGetExams(id:any){
    this.workspaceService.getWorkSpaceExams(id).subscribe({
      next:(response)=>{
        console.log(response.result);
        this.exams = response.result;
        if (localStorage.getItem('role') === 'ADMIN'){
          for (let exam in this.exams){
              this.exams = this.exams.filter(i=>i.student ==0);
          }
          console.log(this.exams);
        }else {
          for (let exam in this.exams){
            this.exams = this.exams.filter(i=>i.student ==localStorage.getItem('id'));
          }
          console.log(this.exams);
        }


      },
      error:(e)=>{
        console.log(e);
      },
      complete:()=>{

      }
    })
  }
  onFindUser():any{
    console.log("email : ",this.emailForm.value.email)
    this.userService.getUserDtoByEmail(this.emailForm.value.email).subscribe({
      next:(response)=>{
        console.log("User Response",response);
        this.user = response.result;
        this.emailForm.reset();
      },
      error:(e)=>{
        console.log(e);
      },
      complete:()=>{
        this.loading=!this.loading;
        console.log(this.loading);
      }
    })

  }


  onAddUser(){
    this.workspaceService.inviteUser(this.user.id,this.id).subscribe({
      next:(response)=>{
        console.log(response);
      },
      error:(err)=>{
        console.log(err);
      },
      complete:()=>{
      }
    })
  }

  onRemoveUser(id:any){
    this.workspaceService.RemoveUserFromWs(id,this.id).subscribe({
      next:(response)=>{
        console.log(response);
      },
      error:(err)=>{
        console.log(err);
      },
      complete:()=>{
        console.log("removed user with id " , id);

      }
    })

  }
  onsetUser(user:any){
    this.user2.id=user.id;
    this.user2.firstname=user.firstname;
    this.user2.lastname=user.lastname;
    this.user2.email=user.email;

  }


  protected readonly localStorage = localStorage;

  onGotoWs(id:any) {
    this.router.navigate(['/Workspace',id]);

  }
}
