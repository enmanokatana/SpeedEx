import {Component, OnInit} from '@angular/core';
import {ExamService} from "../../../core/services/exam/exam.service";
import {ActivatedRoute, Router} from "@angular/router";
import {NgForOf, NgIf} from "@angular/common";

@Component({
  selector: 'app-exam-details',
  standalone: true,
  imports: [
    NgForOf,
    NgIf
  ],
  templateUrl: './exam-details.component.html',
  styleUrl: './exam-details.component.css'
})

export class ExamDetailsComponent implements OnInit{
  exam:any;
  examId:any;
  questions:any[]= [];
  constructor(private examService:ExamService,
              private route:ActivatedRoute,
              private router:Router) {

  }

  ngOnInit() {
    this.examId = this.route.snapshot.paramMap.get('examId');
    this.onLoadExamInformation();
  }

  onLoadExamInformation(){
    this.examService.GetExamInfo(this.examId).subscribe({
      next:(response:any)=>{
        console.log(response);
        this.exam = response.result;
        this.questions = response.result.questions
      },
      error:(e)=>{
        console.log(e)
      },
      complete:()=>{

      }    })
  }

}
