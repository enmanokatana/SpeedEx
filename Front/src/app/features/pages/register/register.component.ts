import { Component, OnInit } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import {
  FormBuilder,
  FormControl,
  FormGroup,
  Validators,
} from '@angular/forms';
import { AuthService } from '../../../core/services/Auth/auth.service';
import {NgIf} from "@angular/common";
@Component({
  selector: 'app-register',
  standalone: true,
  imports: [ReactiveFormsModule, NgIf],
  templateUrl: './register.component.html',
  styleUrl: './register.component.css',
})
export class RegisterComponent implements OnInit {
  errorMessages: { [key: string]: string } = {};
  step:number =  1;
  registerRequest: any = {
    firstname: '',
    lastname: '',
    email: '',
    password: '',
    role:''
  };
  constructor(
    private service: AuthService,
    private router: Router,
    private builder: FormBuilder
  ) {}

  public registerForm!: FormGroup;

  ngOnInit() {
    this.registerForm = this.builder.group({
      firstname: this.builder.control('', Validators.required),
      lastname: this.builder.control('',Validators.required),
      email: this.builder.control(
        '',
        Validators.compose([Validators.required, Validators.email])
      ),
      password: this.builder.control(
        '',
        Validators.compose([Validators.required, Validators.minLength(8)])
      ),
      confirm_password: this.builder.control(
        '',
        Validators.compose([Validators.required, Validators.minLength(8)])
      ),
    });
  }

  onRegister(role:string) {
    console.log(this.registerForm.value);
    if (
      this.registerForm.get('password')?.value?.trim() !==
        this.registerForm.get('confirm_password')?.value?.trim() &&
      this.registerForm.get('password')?.value?.length > 0 &&
      this.registerForm.get('confirm_password')?.value?.length > 0
    ) {
      this.errorMessages['equal'] = 'Passwords do not match';
    }

    if (this.registerForm.valid) {
      this.registerRequest = {
        firstname : this.registerForm.value.firstname,
        lastname :this.registerForm.value.lastname,
        email : this.registerForm.value.email,
        password : this.registerForm.value.password,
        role : role
      };
      this.service.register(this.registerRequest).subscribe({
        next: (response) => {
          console.log(response);
          this.router.navigate(['/Home'], {
            queryParams: { registration: true },
          });
        },
      error:  (error) => {
          console.log(error);
        },
      complete: () => console.info('complete'),
      }

      );
  }else console.log('Register form invalid ');

  }

  get firstname(): FormControl {
    return this.registerForm.get('firstname') as FormControl;
  }
  get lastname(): FormControl {
    return this.registerForm.get('lastname') as FormControl;
  }

  get Email(): FormControl {
    return this.registerForm.get('email') as FormControl;
  }
  get Password(): FormControl {
    return this.registerForm.get('password') as FormControl;
  }
  get Confirm_Password(): FormControl {
    return this.registerForm.get('confirm_password') as FormControl;
  }
  get RoleName(): FormControl {
    return this.registerForm.get('rolename') as FormControl;
  }
  advance(){
    if (this.registerForm.valid) {
      this.step = 2;
    }
  }
  GoBack(){
    this.step= 1;
  }
}

