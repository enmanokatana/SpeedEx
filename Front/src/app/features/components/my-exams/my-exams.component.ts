import {Component, ElementRef, Input, OnInit, ViewChild} from '@angular/core';
import {DatePipe, NgForOf, NgIf, NgOptimizedImage} from "@angular/common";
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
    FormsModule,
    DatePipe
  ],
  templateUrl: './my-exams.component.html',
  styleUrl: './my-exams.component.css'
})
export class MyExamsComponent implements OnInit{
  id:any;
  showButton:boolean = false;
  exams:any[]=[];
  filteredExams: any[] = [];
  selectedFile:File |null = null;
  @ViewChild('fileInput', { static: false }) fileInput!: ElementRef;

  workSpaceName:string="";

  image:string = '';
  sortOption: string = 'date'; // Default sort option
  searchQuery: string = '';


  ngOnInit() {
    this.id = this.route.parent?.snapshot.params['id']  ;
    this.onGetWsImage();
    this.onGetExams(this.id);
    this.onGetWorkSpacesName();

  }

  constructor(private userService:UserService,
              private workspaceService:WorkspaceService,
              private route:ActivatedRoute,
              private router:Router,
              private builder: FormBuilder) {
  }
  triggerFileInput(): void {
    this.fileInput.nativeElement.click();
  }
  onFileSelected(event:any){
    const file = event.target.files[0];
    if (file) {
      this.selectedFile = file;
      this.uploadImage();
    }
  }
  onGetWorkSpacesName(){
    this.workspaceService.getName(this.id).subscribe({
      next:(response:any)=> {
        console.log(response);
        this.workSpaceName = response.result;
      }
    })
  }
  uploadImage():void{
    console.log('ejbdejb')
    if (this.selectedFile){
      const formData = new FormData();
      formData.append('file', this.selectedFile);
      this.workspaceService.changeWorkSpaceImage(this.id,formData).subscribe({
        next:(response)=>{
          console.log("image added",response);
          this.image = response;
        },
        error:(e)=>{
          console.log(e);
        }
      })
    } else {
      console.error('No file selected');
    }
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
      this.workspaceService.getWorkSpaceExamsForAdmin(id).subscribe({
        next:(response)=>{
          console.log("Exams For Admin : ",response.result);
          this.exams = response.result;
          },
        error:(e)=>{console.log(e);},
        complete:()=>{this.filteredExams = this.exams;}
      });
    }
    else
    {
      this.workspaceService.getWorkSpaceExamsForUser(id,localStorage.getItem('id')).subscribe({
        next:(response)=>{
          console.log("Exams fOR User : ",response.result);
          this.exams = response.result;

            for (let exam in this.exams){
              this.exams = this.exams.filter(i=>i.student ==localStorage.getItem('id'));
            }
            console.log(this.exams);
            },
        error:(e)=>{console.log(e);},
        complete:()=>{this.filteredExams = this.exams;}});
    }
  }

  filterExams(): void {
    // Filter exams based on user role and id
    const userId = localStorage.getItem('id');


    // Sort filtered exams based on sortOption
    switch (this.sortOption) {
      case 'date':
       this.filteredExams.sort((a, b) => {
          // Ensure both a and b have a valid createdOn date
          if (!a.createdOn || !b.createdOn) return 0;

          return new Date(b.createdOn).getTime() - new Date(a.createdOn).getTime();
        });
        break;
      case 'name':
        this.filteredExams.sort((a, b) => {
          // Ensure both a and b have a valid name
          if (!a.name || !b.name) return 0;

          return a.name.localeCompare(b.name);
        });
        break;
      default:
        break;
    }


    if (this.searchQuery) {
      this.filteredExams = this.exams.filter(exam =>
        exam.name.toLowerCase().includes(this.searchQuery.toLowerCase())
      );
    }else {
      this.filteredExams = [...this.exams];
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
