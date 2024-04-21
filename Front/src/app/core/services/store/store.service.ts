import { Injectable } from '@angular/core';
import { JwtTokenService } from '../JwtToken/jwt-token.service';

@Injectable({
  providedIn: 'root',
})
export class StoreService {
  constructor(private decoder:JwtTokenService) {}

  setToken(token: string) {
    localStorage.setItem('token', token);
  }
  setProfilePic(image : string ){
    localStorage.setItem('image',image);
  }
  getImage():string|null{
    return  localStorage.getItem('image');
  }

  getToken(): string | null {
    return localStorage.getItem('token');
  }

  isLogged(): boolean {
    if (this.getToken() != null) {
      return true;
    }
    return false;
  }
  getUser():any{

    let CurrentToken  = this.getToken();
    if(CurrentToken!=null){
    const decoded = this.decoder.DecodeToken(CurrentToken);
    console.log(decoded);
    return decoded;
}


  }




}
