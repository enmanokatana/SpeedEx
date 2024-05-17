import {Component, OnInit} from '@angular/core';
import {WorkspaceService} from "../../../core/services/workspace/workspace.service";
import {ActivatedRoute, Router} from "@angular/router";
import {FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators} from "@angular/forms";
import {UserService} from "../../../core/services/User/user.service";
import {NgIf} from "@angular/common";

@Component({
  selector: 'app-create-workspace',
  standalone: true,
  imports: [
    FormsModule,
    NgIf,
    ReactiveFormsModule
  ],
  templateUrl: './create-workspace.component.html',
  styleUrl: './create-workspace.component.css'
})
export class CreateWorkspaceComponent implements OnInit{

  currentStep:any = 0;
  loading:boolean = true;
  addButton:boolean=false;
  id!:any;
  workspace:any={
    id:0,
    name:'',
    description:'',
    admin:0,
    users:[]
  };
  user:any={};
constructor(private workspaceService:WorkspaceService,
            private router:Router,
            private route:ActivatedRoute,
            private builder:FormBuilder,
            private userService:UserService) {}

  workspaceForm!:FormGroup;
ngOnInit() {
  this.id = this.route.snapshot.paramMap.get('id');
  this.workspaceForm = this.builder.group({
    name:this.builder.control('',Validators.required),
    description:this.builder.control('',Validators.required),
    email:this.builder.control('',Validators.email)
  });
}
onCreateWorkspace(step:any){
  if (step==0){
    console.log("WorkSpace name : ",this.workspaceForm.value.name);
    this.workspace.name = this.workspaceForm.value.name;
    this.currentStep++;
  }else if (step==1){
    console.log("WorkSpace description : ",this.workspaceForm.value.description);
    this.workspace.description = this.workspaceForm.value.description;
    this.currentStep++;
  }else if (step==2){
    this.workspace.admin = this.id;
    this.workspaceService.createWorkspace(this.workspace).subscribe({
      next:(response)=>{
        console.log(response);
        this.router.navigate(['/Home'])
      },
      error:(e)=>{
        console.log(e);
      },
      complete:()=>{
      }

    })


  }
}

onFindUser():any{
  this.userService.getUserDtoByEmail(this.workspaceForm.value.email).subscribe({
    next:(response)=>{
      console.log(response);
      this.user = response.result;
      this.workspaceForm.reset();
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
  this.workspace.users.push(this.user.id);
  console.log(this.workspace);
}




}
