import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {environment} from "../../../../environmenets/environment";

@Injectable({
  providedIn: 'root'
})
export class WorkspaceService {

  url =environment.API_BASE_URL+"api/v1/Workspace"
  iurl = environment.API_BASE_URL+"api/v1/Invitations"
  token = localStorage.getItem('token');
  constructor(private http:HttpClient) { }


  getWorkSpaceDto(id:any):Observable<any>{

    const RequestUrl  = `${this.url}/dto/user/${id}`;
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${this.token}`
    });
    return this.http.get(RequestUrl, {headers});
  }
  getWorkSpaces(id:any):Observable<any>{

    const RequestUrl  = `${this.url}/user/${id}`;
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${this.token}`
    });
    return this.http.get(RequestUrl, { headers });
  }

  getWorkSpaceUsersIds(id:any):Observable<any>{
    const requestUrl = `${this.url}/users/${id}`;
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${this.token}`
    });
    return this.http.get(requestUrl, { headers });
  }

  getWorkSpaceAdmin(id:any):Observable<any>{
    const requestUrl = `${this.url}/admin/${id}`;
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${this.token}`
    });
    return this.http.get(requestUrl, { headers });

  }
  createWorkspace(workspace:any):Observable<any>{
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${this.token}`

      // Add any other headers required by your API
    });
    return this.http.post(this.url,workspace,{headers});
  }
    getWorkSpaceExams(id:any):Observable<any>{
    const requestUrl = `${this.url}/${id}/exams`;
      const headers = new HttpHeaders({
        'Authorization': `Bearer ${this.token}`
      });
    return this.http.get(requestUrl, { headers });


  }
  addUserToWorkspace(userId : any,id:any):Observable<any>{
    const requestUrl = `${this.url}/${id}/adduser/${userId}`;
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${this.token}`

      // Add any other headers required by your API
    });
    return  this.http.put(requestUrl,null,{headers});

  }
  RemoveUserFromWs(userId:any,WiD:any
  ):Observable<any>{
    const requestUrl = `${this.url}/${WiD}/user/${userId}`;
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${this.token}`
    });
    return  this.http.delete(requestUrl, { headers });

  }
  inviteUser(userId:any,wsId:any){
    const requestUrl = `http://localhost:8080/api/v1/Invitations/user/${userId}/Ws/${wsId}`
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${this.token}`

      // Add any other headers required by your API
    });
    console.log(this.token);
    return this.http.post(requestUrl,{headers});
  }
  getAllInvites(userId:any){
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${this.token}`

      // Add any other headers required by your API
    });

    return this.http.get(`${this.iurl}/${userId}`, { headers });

  }



}
