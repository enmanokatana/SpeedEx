import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class WorkspaceService {

  url ="http://localhost:8080/api/v1/Workspace"
  constructor(private http:HttpClient) { }


  getWorkSpaces(id:any):Observable<any>{

    const RequestUrl  = `${this.url}/user/${id}`;
    return this.http.get(RequestUrl);
  }
}
