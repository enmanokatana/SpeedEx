import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class QuestionService {


  url = "http://localhost:8080/api/v1/Question";
  constructor(private  http:HttpClient) { }


  getQuestionById(id:any):Observable<any>{
    const requestUrl = `${this.url}/${id}`;
    return this.http.get(requestUrl);

  }

  PassExam(questions : any):Observable<any>{
    const requestUrl = `${this.url}/passExam`;

    const headers = new HttpHeaders({
      'Content-Type': 'application/json'
      // Add any other headers required by your API
    });
    return this.http.post(requestUrl,questions,{headers});

  }
}
