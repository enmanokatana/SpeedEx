import {ActivatedRouteSnapshot, CanActivateFn, Router, RouterStateSnapshot, UrlTree} from '@angular/router';
import {inject, Injectable} from "@angular/core";
import {StoreService} from "../services/store/store.service";
import {Observable} from "rxjs";


export const AuthGuard: CanActivateFn = (
  route: ActivatedRouteSnapshot,
  state: RouterStateSnapshot
):boolean|UrlTree|Observable<boolean | UrlTree> => {
  return inject(StoreService).isLogged()
    ? true
    : inject(Router).createUrlTree(['Login']);

};
