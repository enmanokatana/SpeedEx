import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class ExamService {

  url = "http://localhost:8080/api/v1/Exam";
  constructor(private http:HttpClient) {

  }

  CreateExam(exam : any):Observable<any>{
    const headers = new HttpHeaders({
      'Content-Type': 'application/json'
      // Add any other headers required by your API
    });
    return this.http.post(this.url,exam,{headers});

  }
  GetExamInfo(id:any):Observable<any>{
    const requestUrl = `${this.url}/${id}`;

    return this.http.get(requestUrl);


  }

  getQuestionIdsByExam(id:any):Observable<any>{
    const requestUrl = `${this.url}/questionsIds/${id}`;
    return this.http.get(requestUrl);

  }



}
