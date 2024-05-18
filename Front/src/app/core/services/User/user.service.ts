import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";
import {Observable} from "rxjs";
import {environment} from "../../../../environmenets/environment";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http:HttpClient) { }

  url :string = environment.API_BASE_URL+"api/v1/User";
  token = localStorage.getItem('token');


  GetUser(id:any):Observable<any>{

    const RequestUrl =`${this.url}/${id}`
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${this.token}`
    });
    return this.http.get(RequestUrl,{headers});


  }
  getUserWorkSpacesIds(id:any):Observable<any>{
    const requestUrl = `${this.url}/workspacesids/${id}`;
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${this.token}`
    });
    return this.http.get(requestUrl,{headers});

  }

  getWorkSpaceUsers(ids: any): Observable<any> {
    const requestUrl = `${this.url}/usersdtos`;

    const headers = new HttpHeaders({
      'Content-Type':'application/json',

      'Authorization': `Bearer ${this.token}`
    });

    // Constructing HTTP params
    let params = new HttpParams();
    for (const key in ids) {
      if (ids.hasOwnProperty(key)) {
        params = params.append(key, ids[key]);
      }
    }

    return this.http.get<any>(requestUrl, { headers, params });
  }
  getUserDto(id:any):Observable<any>{
    const requestUrl = `${this.url}/userdto/${id}`;
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${this.token}`
    });
    return this.http.get(requestUrl,{headers});
  }
  getUserDtoByEmail(email:string):Observable<any>{
    const requestUrl = `${this.url}/emaildto/${email}`;
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${this.token}`
    });
    return this.http.get(requestUrl,{headers});
  }

}
