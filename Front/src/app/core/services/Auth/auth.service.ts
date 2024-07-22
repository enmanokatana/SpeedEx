import {HttpClient, HttpErrorResponse, HttpHeaders} from '@angular/common/http';
import { Injectable } from '@angular/core';
import {catchError, Observable, tap, throwError} from 'rxjs';
import { LoginResponseDto } from '../../Models/login-response-dto';
import { StoreService } from '../store/store.service';
import {environment} from "../../../../environmenets/environment";
import * as jwt_decode from "jwt-decode";
import {Router} from "@angular/router";

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  url: string = environment.API_BASE_URL;
  token = localStorage.getItem('token');

  constructor(private http: HttpClient, private store: StoreService,private router:Router) {}

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
    console.log("starting logout ...");

    const headers = new HttpHeaders({
      'Authorization': `Bearer ${this.token}`
    });

    return this.http.post(`${this.url}logout-handler`, { headers }).subscribe({
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

  isTokenExpired(token: string): boolean {
    try {
      const decoded: any = jwt_decode.jwtDecode(token);
      if (decoded.exp * 1000 < Date.now()) { // Check if token is expired
        return true;
      } else {
        return false;
      }
    } catch (error) {
      console.error('Error decoding token:', error);
      return true; // Consider token expired if decoding fails
    }
  }

  logoutIfTokenExpired(): void {
    const token = localStorage.getItem('token');

    if (token && this.isTokenExpired(token)) {
      this.logout();
      this.router.navigate(['/login']); // Example route to navigate on logout
    }
  }


}
