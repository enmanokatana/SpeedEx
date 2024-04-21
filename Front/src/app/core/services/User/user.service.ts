import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http:HttpClient) { }

  url :string = "http://localhost:8080/api/v1/User";


  GetUser(id:any):Observable<any>{

    const RequestUrl =`${this.url}/${id}`
    return this.http.get(RequestUrl);

  }

}
