import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {environment} from "../../../../environmenets/environment";

@Injectable({
  providedIn: 'root'
})
export class ExamService {

  url = environment.API_BASE_URL+"api/v1/Exam";
  token = localStorage.getItem('token');

  constructor(private http:HttpClient) {

  }

  CreateExam(exam : any):Observable<any>{
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${this.token}`

      // Add any other headers required by your API
    });
    return this.http.post(this.url,exam,{headers});

  }
  GetExamInfo(id:any):Observable<any>{
    const requestUrl = `${this.url}/${id}`;
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${this.token}`
    });
    return this.http.get(requestUrl,{headers});


  }

  getQuestionIdsByExam(id:any):Observable<any>{
    const requestUrl = `${this.url}/questionsIds/${id}`;
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${this.token}`
    });
    return this.http.get(requestUrl,{headers});

  }



}
