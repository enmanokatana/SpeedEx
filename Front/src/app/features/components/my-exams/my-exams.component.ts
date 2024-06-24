import {Component, OnInit} from '@angular/core';
import {NgForOf, NgIf, NgOptimizedImage} from "@angular/common";
import {FormBuilder, ReactiveFormsModule} from "@angular/forms";
import {UserService} from "../../../core/services/User/user.service";
import {WorkspaceService} from "../../../core/services/workspace/workspace.service";
import {ActivatedRoute, Router, RouterLink} from "@angular/router";

@Component({
  selector: 'app-my-exams',
  standalone: true,
  imports: [
    NgForOf,
    NgIf,
    ReactiveFormsModule,
    RouterLink,
    NgOptimizedImage
  ],
  templateUrl: './my-exams.component.html',
  styleUrl: './my-exams.component.css'
})
export class MyExamsComponent implements OnInit{
  id:any;
  exams:any[]=[];
  image:string = '';

  ngOnInit() {
    this.id = this.route.parent?.snapshot.params['id']  ;
    this.onGetWsImage();
    this.onGetExams(this.id);

  }

  constructor(private userService:UserService,
              private workspaceService:WorkspaceService,
              private route:ActivatedRoute,
              private router:Router,
              private builder: FormBuilder) {
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

  onGetExams(id:any){
    if (localStorage.getItem('role') === 'ADMIN'){
      this.workspaceService.getWorkSpaceExams(id).subscribe({
        next:(response)=>{
          console.log("Exams : ",response.result);
          this.exams = response.result;
          if (localStorage.getItem('role') === 'ADMIN'){
            for (let exam in this.exams){
              this.exams = this.exams.filter(i=>i.student ==0);
            }
            //console.log(this.exams);
          }else {
            for (let exam in this.exams){
              this.exams = this.exams.filter(i=>i.student ==localStorage.getItem('id'));
            }
            //console.log(this.exams);
          }


        },
        error:(e)=>{
          //console.log(e);
        },
        complete:()=>{

        }
      });
    }else {
      this.workspaceService.getWorkSpaceExamsForUser(id,localStorage.getItem('id')).subscribe({
        next:(response)=>{
          console.log("Exams : ",response.result);
          this.exams = response.result;
          if (localStorage.getItem('role') === 'ADMIN'){
            for (let exam in this.exams){
              this.exams = this.exams.filter(i=>i.student ==0);
            }
            //console.log(this.exams);
          }else {
            for (let exam in this.exams){
              this.exams = this.exams.filter(i=>i.student ==localStorage.getItem('id'));
            }
            //console.log(this.exams);
          }


        },
        error:(e)=>{
          //console.log(e);
        },
        complete:()=>{

        }
      });
    }

  }




  protected readonly localStorage = localStorage;
}
