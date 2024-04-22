import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
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
  getWorkSpaceUsersIds(id:any):Observable<any>{
    const requestUrl = `${this.url}/users/${id}`;
    return this.http.get(requestUrl);
  }

  getWorkSpaceAdmin(id:any):Observable<any>{
    const requestUrl = `${this.url}/admin/${id}`;
    return this.http.get(requestUrl);

  }
  createWorkspace(workspace:any):Observable<any>{
    const headers = new HttpHeaders({
      'Content-Type': 'application/json'
      // Add any other headers required by your API
    });
    return this.http.post(this.url,workspace,{headers});
  }



}
