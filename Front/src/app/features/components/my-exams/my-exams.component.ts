import {Component, OnInit} from '@angular/core';
import {NgForOf, NgIf, NgOptimizedImage} from "@angular/common";
import {FormBuilder, FormsModule, ReactiveFormsModule} from "@angular/forms";
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
    NgOptimizedImage,
    FormsModule
  ],
  templateUrl: './my-exams.component.html',
  styleUrl: './my-exams.component.css'
})
export class MyExamsComponent implements OnInit{
  id:any;
  exams:any[]=[];
  filteredExams: any[] = [];

  image:string = '';
  sortOption: string = 'date'; // Default sort option
  searchQuery: string = '';


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
          this.filteredExams = this.exams;

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


  filterExams(): void {
    // Filter exams based on user role and id
    const userId = localStorage.getItem('id');
    if (localStorage.getItem('role') === 'ADMIN') {
      this.filteredExams = this.exams.filter(exam => exam.student === 0);
    } else {
      this.filteredExams = this.exams.filter(exam => exam.student === userId);
    }


    // Sort filtered exams based on sortOption
    switch (this.sortOption) {
      case 'date':
        this.filteredExams.sort((a, b) => new Date(a.date).getTime() - new Date(b.date).getTime());
        break;
      case 'id':
        this.filteredExams.sort((a, b) => a.id - b.id);
        break;
      case 'name':
        this.filteredExams.sort((a, b) => a.name.localeCompare(b.name));
        break;
      default:
        break;
    }

    if (this.searchQuery) {
      this.filteredExams = this.filteredExams.filter(exam =>
        exam.name.toLowerCase().includes(this.searchQuery.toLowerCase())
      );
    }
  }

  onSearch(): void {
    this.filterExams();
  }
  onChangeSortOption(event: any): void {
    this.sortOption = event.target.value;
    this.filterExams();
  }


  protected readonly localStorage = localStorage;
}
