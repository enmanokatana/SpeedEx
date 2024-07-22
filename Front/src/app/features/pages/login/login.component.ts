import {Component, OnInit} from '@angular/core';
import {AuthService} from '../../../core/services/Auth/auth.service';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {Router, RouterLink} from '@angular/router';
import {StoreService} from '../../../core/services/store/store.service';
import {NgIf} from "@angular/common";
import {ToastService} from "../../../core/services/toast/toast.service";
import {ToastType} from "../../../core/enums/ToastType";
import {HttpErrorResponse} from "@angular/common/http";

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    RouterLink,
    NgIf
  ],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css',
  providers:[AuthService]
})
export class LoginComponent implements OnInit {
  token: string = '';
  worked:boolean=false;
  loginrequest: any = {
    email: '',
    password: '',
  };

  constructor(
    private service: AuthService,
    private builder: FormBuilder,
    private router: Router,
    private toastService:ToastService,
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
        this.store.setUserRole(response.role);
          this.store.setToken(response.ACCESS_TOKEN);
        this.router.navigate(['Home']);



      },
      error: (error:HttpErrorResponse) => {
        console.error('Login error', error);
        let errorMessage = '';
        if (error.error instanceof ErrorEvent) {
          // Client-side error
          errorMessage = `Error: ${error.error.message}`;
        } else {
          switch (error.status) {
            case 401:
              errorMessage = 'Username or password Incorrect';
              break;
            case 403:
              errorMessage = 'Forbidden. You do not have permission to access.';
              break;
            default:
              errorMessage = `Error Code: ${error.status}\nMessage: ${error.message}`;
              break;
          }
        }
        this.toastService.show(errorMessage,3000,ToastType.Error);

      },
      complete: () => {
        this.toastService.show("Logged in successfully ",3000,ToastType.Success);
      },
    });
  }

}

