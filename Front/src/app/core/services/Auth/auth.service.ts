import {HttpClient, HttpErrorResponse, HttpHeaders} from '@angular/common/http';
import { Injectable } from '@angular/core';
import {catchError, Observable, tap, throwError} from 'rxjs';
import { LoginResponseDto } from '../../Models/login-response-dto';
import { StoreService } from '../store/store.service';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  url: string = 'http://localhost:8080/';
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
    console.log(this.store.isLogged());
    localStorage.removeItem('token');
    console.log(this.store.isLogged());
  }
  register(userData: any): Observable<any> {
    console.log(userData);
    const registerUrl = `${this.url}signup`;
    return this.http.post(registerUrl, userData);
  }


}
