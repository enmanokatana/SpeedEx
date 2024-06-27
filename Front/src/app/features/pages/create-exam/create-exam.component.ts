import {Component, OnInit} from '@angular/core';
import {ExamService} from "../../../core/services/exam/exam.service";
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {NgForOf, NgIf} from "@angular/common";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'app-create-exam',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    NgIf,
    NgForOf
  ],
  templateUrl: './create-exam.component.html',
  styleUrl: './create-exam.component.css'
})
export class CreateExamComponent implements OnInit{
  id!:any;
  currentStep:any = 0;
  exam : any = {
    id:0,
    name:'',
    timer:0,
    passingScore:0,
    description:0,
    randomizeQuestions:false,
    isPublic :false,
    difficultyLevel :"EASY",
    questions:[],
    user:localStorage.getItem('id'),
    workspace:0,
    examGroup:0,
    student:0,
    passingDate:null

}
question : any={
    id:0,
  name:'',
  description:'',
  type:'QCM',
  answer:'',
  timer:0,
  options:[],
  difficultyLevel:'EASY'
}

options : any[]=[]
  passingScore:any=0;
  constructor(private examService:ExamService,
              private builder:FormBuilder,
              private router:Router,
              private route:ActivatedRoute) {

  }
  examForm!:FormGroup;
  questionForm!:FormGroup;
  optionForm!:FormGroup;

  displayedQuestion:any;
  ngOnInit() {
    this.id= this.route.snapshot.paramMap.get('id');
    this.examForm = this.builder.group({
      name:this.builder.control('',Validators.required),
      timer:this.builder.control(60,Validators.required),
      passingScore:this.builder.control(100,Validators.required),
      description:this.builder.control('',Validators.required),
      randomizeQuestions:this.builder.control(false,Validators.required),
      isPublic :this.builder.control(false,Validators.required),
      difficultyLevel :this.builder.control('EASY',Validators.required),
      passingDate:this.builder.control(new Date(Date.now()),Validators.required)

    })
    this.questionForm = this.builder.group({
      name:this.builder.control('',Validators.required),
      description:this.builder.control('',Validators.required),
      type:this.builder.control('QCM',Validators.required),
      answer:this.builder.control('',),
      timer:this.builder.control(5,Validators.required),
      difficultyLevel:this.builder.control('EASY',Validators.required),
      score:this.builder.control(10,Validators.required),
    })
    this.optionForm = this.builder.group({
      value:this.builder.control('',Validators.required),
      isCorrect:this.builder.control(false,Validators.required),
    })

  }

  onCreateExam(step:any){
    if (step == 0){
      console.log("Exam name :",this.examForm.value.name);
      this.exam.name = this.examForm.value.name;
      this.currentStep++;
    }else if(step ==1){
      console.log("Exam description :",this.examForm.value.description);
      this.exam.description=this.examForm.value.description;
      this.currentStep++;
    }else if(step ==2){
      console.log("Exam timer :",this.examForm.value.timer);
      this.exam.timer = this.examForm.value.timer;
      this.currentStep++;
    }else if(step ==3){
      console.log("Exam RandomQs :",this.examForm.value.randomizeQuestions);
      this.exam.randomizeQuestions = this.examForm.value.randomizeQuestions;
      this.currentStep++;
    }else if(step ==4){
      console.log("Exam public :",this.examForm.value.isPublic);
      this.exam.isPublic = this.examForm.value.isPublic;
      this.currentStep++;
    }else if(step ==5){
      console.log("Exam Diff :",this.examForm.value.difficultyLevel);
      this.exam.difficultyLevel = this.examForm.value.difficultyLevel;
      this.currentStep++;
    }else if(step ==6){
      console.log("Exam passingDate :",this.examForm.value.passingDate.toISOString());
      //convert to ISO
      this.exam.passingDate = this.examForm.value.passingDate.toISOString();
      this.currentStep++;
    }else if(step ==7){
      this.passingScore+=this.questionForm.value.score;
      this.question = this.questionForm.value;

      console.log("Q Diff :",this.question);
      console.log("EDiff :",this.examForm.value);

      this.exam.difficultyLevel = this.examForm.value.difficultyLevel;
      if (this.questionForm.controls['type'].value ==='QCM'){
        this.question.options = this.options;
        this.question.answer = ''

      }else {
        this.question.answer = this.questionForm.value.answer;
        this.question.options = null;
      }
      this.exam.questions.push(this.question);

      console.log(this.exam);
      this.question = null;

      this.questionForm.reset();
      this.options = [];


    }



  }
  onAddOption(){
    this.options.push(this.optionForm.value);
    console.log(this.options)
    this.optionForm.reset();

  }
  onSaveExam(){
    this.exam.passingScore = this.passingScore/2;
    this.exam.workspace =this.id;
    this.examService.CreateExam(this.exam).subscribe({
      next:(response)=> {
        console.log(response);
      },
      error:(err)=>{
        console.log(err);
      },
      complete:()=>{
        this.router.navigate(['Home']);
      }
    })

  }

  onBack(){
    this.currentStep--;
  }

  onDeleteQuestion() {
    this.exam.questions = this.exam.questions.filter((question:any)=>question !==this.displayedQuestion);
    console.log(this.exam.questions);

  }

  onModifyQuestion() {
    this.onDeleteQuestion();
    this.questionForm.patchValue(this.displayedQuestion);
    if (this.displayedQuestion.options){
      this.optionForm.reset();
        let tempOptions:any[] = [] ;
        this.displayedQuestion.options.forEach((opt:any)=>{
        tempOptions.push(opt);
      })
      this.options = tempOptions;
    }

  }
}
