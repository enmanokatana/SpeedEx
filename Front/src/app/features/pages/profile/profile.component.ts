import {Component, OnInit} from '@angular/core';
import {UserService} from "../../../core/services/User/user.service";
import {StoreService} from "../../../core/services/store/store.service";
import {NgForOf, NgIf} from "@angular/common";
import {RouterLink} from "@angular/router";
import {HeaderComponent} from "../../../core/componenets/header/header.component";
import {FormBuilder, FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators} from "@angular/forms";

@Component({
  selector: 'app-profile',
  standalone: true,
  imports: [
    NgIf,
    RouterLink,
    NgForOf,
    HeaderComponent,
    FormsModule,
    ReactiveFormsModule
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
  selectedFile:File |null = null;

  imagePreview : string|ArrayBuffer|null = '';
  profileForm!:FormGroup;
  constructor(private userService : UserService,
              private store:StoreService,
              private builder:FormBuilder) {
  }


  ngOnInit(): void {
    this.onGetUser();

    this.profileForm = this.builder.group({
      firstname: this.builder.control('', Validators.required),
      lastname: this.builder.control('',Validators.required)
    })

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
          image:response.result.profileImg
        }
        this.profileForm.patchValue({
          firstname: this.user.firstname,
          lastname: this.user.lastname,
          email: this.user.email,
        });

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
  }


  onFileSelected(event: Event): void {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files[0]) {
      const file = input.files[0];
      if (file){
        this.selectedFile = file;
      }
      const reader = new FileReader();
      reader.onload = () => {
        this.imagePreview = reader.result;
      };
      reader.readAsDataURL(file);

    }
    }
  onUpdateProfilePic(){
    if(this.selectedFile){
      const formData = new FormData();
      formData.append('file', this.selectedFile);
      this.userService.updateProfilePic(formData).subscribe({
        next: (response) => {
          console.log(response);
        }, error: (error) => {
          console.error('Login error', error);
        },
        complete: () => {

        }
      })
    }else {
      console.log("nope ")
    }


    }

 onUpdateProfile(){

    const userDto =  {
      firstname: this.profileForm.value.firstname ,
      lastname: this.profileForm.value.lastname
    }

    this.userService.updateProfile(userDto).subscribe({
      next:(response) => {
        console.log(response) ;
      },error:(error) => {
        console.error('Login error', error);
      },
      complete:() => {
        this.onUpdateProfilePic();


      }
    })
 }

  get firstname(): FormControl {
    return this.profileForm.get('firstname') as FormControl;
  }
  get lastname(): FormControl {
    return this.profileForm.get('lastname') as FormControl;
  }

  get Email(): FormControl {
    return this.profileForm.get('email') as FormControl;
  }
  protected readonly console = console;
}
