import {Component, HostListener, OnInit} from '@angular/core';
import {ActivatedRoute, Router, RouterLink} from "@angular/router";
import {ExamService} from "../../../core/services/exam/exam.service";
import {NgForOf, NgIf} from "@angular/common";
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {QuestionService} from "../../../core/services/question/question.service";
import {filter} from "rxjs";

@Component({
  selector: 'app-exam',
  standalone: true,
  imports: [
    RouterLink,
    NgIf,
    ReactiveFormsModule,
    NgForOf
  ],
  templateUrl: './exam.component.html',
  styleUrl: './exam.component.css'
})
export class ExamComponent implements  OnInit{
  start : boolean = false ;
  loading:boolean = true;
  hours: number = 0;
  minutes: number = 30; // Initial countdown time (30 minutes)
  seconds: number = 0;
  cheating : boolean=false;
  exam!:any ;
  id:any;
  questions:any[]=[];
  currentQusetion:any|undefined={
    name:"",
    description : "",
    type:"NORMAL",
    options:[],
    userAnswer:null,
    userOption:0,
  };
  cheatingTimer:any;
  userAnswers:any[]=[];


  examName:string = "";
  timer:number = 0;
  randomize:boolean = false;
  errorBig:boolean = false;
  isUserActive: boolean = true;






  constructor(private route:ActivatedRoute,
              private router : Router,
              private examService:ExamService,
              private builder:FormBuilder,
             private questionService:QuestionService
              ) {
  }
  optionForm!:FormGroup;
  answerForm!:FormGroup;

  ngOnInit() {
    this.id = this.route.snapshot.paramMap.get('id');

    this.answerForm = this.builder.group({
      answer:this.builder.control('',Validators.required),
    })

    this.optionForm = this.builder.group({
      userOption:this.builder.control(0,Validators.required),
    })
    this.onGetExamInfo();
    this.onGetQuestions();

    setTimeout(() => {
      // Code to execute after the specified delay
      this.loading = !this.loading;
      this.currentQusetion = this.questions[0];
    },2000);
    this.startCountdown();
    this.isVisible();

    this.setupEventListeners();
    this.startTimer()
  }

  setupEventListeners() {
    window.addEventListener('mousemove', this.resetTimer.bind(this));
    window.addEventListener('keydown', this.resetTimer.bind(this));
    // Add more event listeners as needed (e.g., touch events)
  }
  resetTimer() {
    this.isUserActive = true;
    clearTimeout(this.cheatingTimer); // Clear existing timer
    this.startTimer(); // Restart the timer

    // Reset any timer you've set up
    // Implement your timer logic here
  }
  handleUserAFK() {
    this.isUserActive = false;
    this.cheating=!this.cheating;
    console.log("user is cheating")
    // Implement logic to handle AFK state
  }
  @HostListener('document:idle', ['$event'])
  onIdle(event: any) {
    this.handleUserAFK();
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
            this.onSaveExam();
          }
        }
      }
    }, 1000);
  }
  startTimer() {
    this.cheatingTimer = setTimeout(() => {
      this.handleUserAFK();
    }, 20000); // 20 seconds in milliseconds
  }

  onGetExamInfo(){
    this.examService.GetExamInfo(this.id).subscribe({
      next:(response)=>{
        this.examName = response.result.name;
        this.timer = response.result.timer;
        if (60 > this.timer % 60) {
         this.hours = Math.floor(this.timer / 60); // Calculate hours
         this.minutes = this.timer % 60; // Calculate minutes
        }
        this.randomize = response.result.randomizeQuestions;
      },
      error:(err)=>{
        this.errorBig =!this.errorBig;
      }
    })

  }

  onGetQuestions(){
    this.examService.getQuestionIdsByExam(this.id).subscribe(
      {
        next: (response) => {
          let ids;
          if (this.randomize){
             ids = this.shuffleArray(response.result);

          }else {
              ids = this.shuffleArray(response.result);
          }
          for (let id of ids){
            this.questionService.getQuestionById(id).subscribe({
              next:(response)=>{
                this.questions.push(response.result);
                this.currentQusetion.name = this.questions[0].name;
                this.currentQusetion.description = this.questions[0].description;
                this.currentQusetion.type = this.questions[0].type;
                if (this.questions[0].type ==='QCM'){
                  this.currentQusetion.options = this.questions[0].options;

                }

              }
            })
          }

        },
        error:(err)=>{
          this.errorBig =!this.errorBig;
        },
        complete:()=>{

        }

      }
    )

  }
  shuffleArray(array: number[]): number[] {
    const newArray = array.slice(); // Create a copy of the original array
    for (let i = newArray.length - 1; i > 0; i--) {
      const j = Math.floor(Math.random() * (i + 1)); // Generate a random index
      // Swap elements at i and j
      const temp = newArray[i];
      newArray[i] = newArray[j];
      newArray[j] = temp;
    }
    return newArray;
  }
  onSaveQuestion(){


    if (this.userAnswers.length!=0){
      this.userAnswers = this.userAnswers.filter(q=>q.id !==this.currentQusetion.id);
    }

    if (this.currentQusetion.type === 'NORMAL'){
      this.currentQusetion.answer = this.answerForm.value.answer;
      console.log(this.answerForm.value);
      this.answerForm.reset();
    }else {
      this.currentQusetion.userOption = this.optionForm.value.userOption as number;
      console.log(this.optionForm.value.userOption as number);
      this.optionForm.reset();
    }
    console.log(this.currentQusetion);
    this.userAnswers.push(this.currentQusetion);
    console.log("new Answers",this.userAnswers);
  }

  onSaveExam(){

    this.questionService.PassExam(this.userAnswers).subscribe({
      next:(response)=>{
      },
      error:(err)=>{
        this.errorBig =!this.errorBig;
        console.log(err);
      },
      complete:()=>{
        this.router.navigate(['/Home']);
      }
    })


  }






}
