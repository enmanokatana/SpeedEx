import {ActivatedRouteSnapshot, CanActivateFn, Router, RouterStateSnapshot, UrlTree} from '@angular/router';
import {inject} from "@angular/core";
import {StoreService} from "../services/store/store.service";

export const AdminGuard: CanActivateFn =
  (route : ActivatedRouteSnapshot,
state:RouterStateSnapshot):boolean|UrlTree => {
  if (inject(StoreService).getUserRole() === 'ADMIN'){
    return true;
  }
  return inject(Router).createUrlTree(['Home']);

};
