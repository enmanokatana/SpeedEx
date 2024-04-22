import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";
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
  getWorkSpaceUsers(ids: any): Observable<any> {
    const requestUrl = `${this.url}/usersdtos`;
    const headers = new HttpHeaders().set('Content-Type', 'application/json');

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
    return this.http.get(requestUrl);
  }
  getUserDtoByEmail(email:string):Observable<any>{
    const requestUrl = `${this.url}/emaildto/${email}`;
    return this.http.get(requestUrl);
  }

}
