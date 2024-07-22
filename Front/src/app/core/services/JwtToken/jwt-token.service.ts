import { Injectable } from '@angular/core';
import * as jwt_decode from 'jwt-decode';
import {AuthService} from "../Auth/auth.service";
import {Router} from "@angular/router";
@Injectable({
  providedIn: 'root',
})
export class JwtTokenService {
  constructor() {}

  DecodeToken(token:string|null):any{
    if (token){
      return jwt_decode.jwtDecode(token);
    }
    return null;
  }






}
