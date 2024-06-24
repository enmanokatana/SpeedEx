import {Component, OnInit} from '@angular/core';
import {ExamService} from "../../../core/services/exam/exam.service";
import {ActivatedRoute, Router, RouterLink} from "@angular/router";
import {UserService} from "../../../core/services/User/user.service";
import {NgForOf, NgIf} from "@angular/common";

@Component({
  selector: 'app-users-results',
  standalone: true,
  imports: [
    NgForOf,
    NgIf,
    RouterLink
  ],
  templateUrl: './users-results.component.html',
  styleUrl: './users-results.component.css'
})
export class UsersResultsComponent implements OnInit{
  examId:any;
  exams:any[] = [];
  examswithpersons:any[]=[];
  user:any;
  type:any={
    id:0,
    firstname:'',
    lastname:'',
    email:'',
    profileImg:'',
    passed:false,
    passingScore:0,
    result:'',
  }
  ngOnInit() {
    this.examId = this.route.parent?.snapshot.params['id']
    this.onGetExams();
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
          this.type.id = exam.id;
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
