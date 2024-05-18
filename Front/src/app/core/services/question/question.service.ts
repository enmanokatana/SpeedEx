import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {environment} from "../../../../environmenets/environment";

@Injectable({
  providedIn: 'root'
})
export class QuestionService {


  url = environment.API_BASE_URL+"api/v1/Question";
  token = localStorage.getItem('token');

  constructor(private  http:HttpClient) { }


  getQuestionById(id:any):Observable<any>{
    const requestUrl = `${this.url}/${id}`;
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${this.token}`
    });
    return this.http.get(requestUrl,{headers});

  }

  PassExam(questions : any):Observable<any>{
    const requestUrl = `${this.url}/passExam`;

    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${this.token}`

      // Add any other headers required by your API
    });
    return this.http.post(requestUrl,questions,{headers});

  }
}
