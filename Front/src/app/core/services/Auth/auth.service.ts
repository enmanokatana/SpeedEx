import {HttpClient, HttpErrorResponse, HttpHeaders} from '@angular/common/http';
import { Injectable } from '@angular/core';
import {catchError, Observable, tap, throwError} from 'rxjs';
import { LoginResponseDto } from '../../Models/login-response-dto';
import { StoreService } from '../store/store.service';
import {environment} from "../../../../environmenets/environment";

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  url: string = environment.API_BASE_URL;
  token = localStorage.getItem('token');

  constructor(private http: HttpClient, private store: StoreService) {}

  login(userData: any): Observable<any> {
    console.log(userData);
    const url = 'http://localhost:8080/login';
    const headers = new HttpHeaders({
      'Content-Type': 'application/json'
      // Add any other headers required by your API
    });

    return this.http.post(url, userData, { headers });
  }
  logout() {
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${this.token}`
    });
    return this.http.post(this.url, { headers }).subscribe({
      next:()=>{
        localStorage.clear();
        console.log(this.store.isLogged());
      }
    });

  }
  register(userData: any): Observable<any> {
    console.log(userData);
    const registerUrl = `${this.url}signup`;
    return this.http.post(registerUrl, userData);
  }



}
