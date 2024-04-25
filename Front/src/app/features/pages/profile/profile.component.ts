import {Component, OnInit} from '@angular/core';
import {UserService} from "../../../core/services/User/user.service";
import {StoreService} from "../../../core/services/store/store.service";
import {NgForOf, NgIf} from "@angular/common";
import {RouterLink} from "@angular/router";

@Component({
  selector: 'app-profile',
  standalone: true,
  imports: [
    NgIf,
    RouterLink,
    NgForOf
  ],
  templateUrl: './profile.component.html',
  styleUrl: './profile.component.css'
})
export class ProfileComponent implements OnInit{

  showMore:boolean = false;
  user:any={
    firstname : '',
    lastname:'',
    email:'',
    role:'',
    image:''
  }
  files: any = [];

  constructor(private userService : UserService
  ,private store:StoreService) {
  }

  ngOnInit(): void {
    this.onGetUser();

  }

  onGetUser(){
    this.userService.GetUser(localStorage.getItem('id')).subscribe({
      next:(response)=>{
        console.log("second Response",response);
        this.user = {
          firstname:response.result.firstname,
          lastname:response.result.lastname,
          email:response.result.email,
          role:response.result.role,
          image:localStorage.getItem('image')
        }

      },
      error:(error) => {
        console.error('Login error', error);
      },
      complete:() => {
      }
    })
  }
  onShowMore(){
    this.showMore=!this.showMore;
    console.log(this.showMore);
  }

  onDragOver(event: DragEvent): void {
    event.preventDefault();
  }

  onDragLeave(event: DragEvent): void {
    event.preventDefault();
  }
  onDrop(event: DragEvent): void {
    event.preventDefault();
    const files = event.dataTransfer?.files; // Optional chaining
    if (files) {
      this.addFiles(files);
    }
  }
  onFileSelected(event: Event): void {
    const input = event.target as HTMLInputElement;
    if (input.files) {
      this.addFiles(input.files);
    }
  }

  addFiles(files: FileList): void {
    for (let i = 0; i < files.length; i++) {
      this.files.push(files.item(i)!);
    }
  }

  removeFile(file: File): void {
    const index = this.files.indexOf(file);
    if (index !== -1) {
      this.files.splice(index, 1);
    }
  }

  humanFileSize(size: number): string {
    const i = Math.floor(Math.log(size) / Math.log(1024));
    return (
       1 +
      " " +
      ["B", "kB", "MB", "GB", "TB"][i]
    );
  }
  protected readonly console = console;
}
