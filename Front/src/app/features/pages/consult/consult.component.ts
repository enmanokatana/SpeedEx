import {Component, OnInit} from '@angular/core';
import {ExamService} from "../../../core/services/exam/exam.service";
import {ActivatedRoute, Router, RouterOutlet} from "@angular/router";
import {routes} from "../../../app.routes";
import {HeaderComponent} from "../../../core/componenets/header/header.component";
import {NgForOf, NgIf} from "@angular/common";
import {UserService} from "../../../core/services/User/user.service";

@Component({
  selector: 'app-consult',
  standalone: true,
  imports: [
    HeaderComponent,
    NgForOf,
    NgIf,
    RouterOutlet
  ],
  templateUrl: './consult.component.html',
  styleUrl: './consult.component.css'
})
export class ConsultComponent implements  OnInit{
  examId:any;
  exams:any[] = [];
  examswithpersons:any[]=[];
  user:any;
  type:any={
    firstname:'',
    lastname:'',
    email:'',
    profileImg:'',
    passed:false,
    passingScore:0,
    result:'',
  }
  ngOnInit() {

  }

  constructor(private examService:ExamService,
              private route:ActivatedRoute,
              private router:Router,
              private userService:UserService) {

  }


  onGetExams(){
    this.examService.getExamsByGroupId(this.examId).subscribe(
      {
        next: (response: any) => {
          console.log(response);
          this.exams = response.result;
          this.onFindUser();
        },
        error: (e) => {
          console.log(e)
        },
        complete: () => {

        }
      }
    )
  }
  onFindUser():any{
    for (let exam of this.exams){
      this.userService.getUserDto(exam.student).subscribe({
        next:(response)=>{
          console.log("User Response",response);
          this.type.firstname = response.result.firstname;
          this.type.lastname = response.result.lastname;
          this.type.passed = exam.passed;
          this.type.passingScore = exam.passingScore;
          this.type.profileImg = response.result.profileImg;
          this.type.result = exam.result;
          this.examswithpersons.push(this.type);
        },
        error:(e)=>{
          console.log(e);
        },
        complete:()=>{

        }
      })
    }


  }


}
