import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../../core/services/Auth/auth.service';
import { FormBuilder, FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { LoginRequestDto } from '../../../core/Models/login-request-dto';
import { StoreService } from '../../../core/services/store/store.service';
import {UserService} from "../../../core/services/User/user.service";

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    RouterLink
  ],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css',
  providers:[AuthService]
})
export class LoginComponent implements OnInit {
  token: string = '';
  loginrequest: any = {
    email: '',
    password: '',
  };

  constructor(
    private service: AuthService,
    private builder: FormBuilder,
    private router: Router,
    private userService:UserService,
    private store:StoreService,
  ) {}
  loginForm!: FormGroup;
  ngOnInit(): void {
    this.loginForm = this.builder.group({
      email: this.builder.control(
        '',
        Validators.compose([Validators.required, Validators.email])
      ),
      password: this.builder.control('', Validators.required),
    });
  }
  onLoging() {
    this.loginrequest = this.loginForm.value;
    this.service.login(this.loginrequest).subscribe({
      next: (response) => {
          console.log("First Response : ",response);
          localStorage.setItem('id',response.userID)
          this.userService.GetUser(response.userID).subscribe({
            next:(response)=>{
              console.log("second Response",response);
              this.store.setProfilePic(response.result.profileImg);

            },
            error:(error) => {
              console.error('Login error', error);
            },
            complete:() => {
              this.router.navigate(['Home']);

            }

          })

      },
      error: (error) => {
        console.error('Login error', error);
      },
      complete: () => {
      },
    });
  }

}

