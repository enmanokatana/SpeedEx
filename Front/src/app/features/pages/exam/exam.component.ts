import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router, RouterLink} from "@angular/router";
import {ExamService} from "../../../core/services/exam/exam.service";
import {NgIf} from "@angular/common";
import {FormBuilder, ReactiveFormsModule} from "@angular/forms";

@Component({
  selector: 'app-exam',
  standalone: true,
  imports: [
    RouterLink,
    NgIf,
    ReactiveFormsModule
  ],
  templateUrl: './exam.component.html',
  styleUrl: './exam.component.css'
})
export class ExamComponent implements  OnInit{
  start : boolean = false ;
  loading:boolean = false;
  hours: number = 0;
  minutes: number = 30; // Initial countdown time (30 minutes)
  seconds: number = 0;
  cheating : boolean=false;
  exam!:any ;
  id:any;

  examName:string = "";
  timer:number = 0;
  randomize:boolean = false;





  constructor(private route:ActivatedRoute,
              private router : Router,
              private examService:ExamService,
              private builder:FormBuilder) {
  }

  ngOnInit() {
    this.id = this.route.snapshot.paramMap.get('id');
    this.startCountdown();
    this.isVisible();
    this.onGetExamInfo();
  }

  isVisible() {
    // Check if the Page Visibility API is supported by the browser
    if (typeof document.hidden !== "undefined") {
      console.log("defined");
      // Listen for visibility change events
      document.addEventListener("visibilitychange", () => {
        if (document.hidden) {
          // The tab is now hidden (user switched to another tab or minimized the browser window)
          console.log("User switched to another tab or minimized the browser window");
          // You can perform actions here such as pausing the exam timer or displaying a warning message
          // For example, set a flag indicating cheating
          this.cheating = true;
          console.log(this.cheating);
        } else {
          // The tab is now visible again
          console.log("User returned to the tab");
          // You can resume any paused actions here
        }
      });
    } else {
      // Page Visibility API is not supported
      console.log("Page Visibility API is not supported by this browser");
      // You may want to implement alternative methods for detecting tab visibility
    }
  }
  startCountdown() {
    // Update the countdown every second
    const intid =  setInterval(() => {
      // Decrement seconds
      this.seconds--;
      if (this.seconds < 0) {
        this.seconds = 59;
        // Decrement minutes if seconds reach 0
        this.minutes--;
        if (this.minutes < 0) {
          this.minutes = 59;
          // Decrement hours if minutes reach 0
          this.hours--;
          if (this.hours < 0) {
            // Stop the countdown if hours reach 0
            console.log('Countdown ended!');
            clearInterval(intid);
          }
        }
      }
    }, 1000);
  }

  onGetExamInfo(){
    this.examService.GetExamInfo(this.id).subscribe({
      next:(response)=>{
        console.log(response);
        this.examName = response.result.name;
        this.timer = response.result.timer;
        if (60 > this.timer % 60) {
          this.hours = Math.floor(this.timer / 60); // Calculate hours
          this.minutes = this.timer % 60; // Calculate minutes
        }
        this.randomize = response.result.randomizeQuestions;
      },
    })

  }

}
