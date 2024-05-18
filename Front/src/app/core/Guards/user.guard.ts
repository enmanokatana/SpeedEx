import {ActivatedRouteSnapshot, CanActivateFn, Router, RouterStateSnapshot, UrlTree} from '@angular/router';
import {Observable} from "rxjs";
import {inject} from "@angular/core";
import {StoreService} from "../services/store/store.service";

export const UserGuard: CanActivateFn =
  (route : ActivatedRouteSnapshot,
   state:RouterStateSnapshot):boolean|UrlTree => {
    if (inject(StoreService).getUserRole() === 'USER'){
      return true;
    }
    return inject(Router).createUrlTree(['Home']);

  };
