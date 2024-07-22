import { Injectable } from '@angular/core';
import {Subject} from "rxjs";
import {ToastType} from "../../enums/ToastType";

@Injectable({
  providedIn: 'root'
})
export class ToastService {

  private toastSubject = new Subject<{ message: string, duration: number,type:ToastType }>();
  toastState = this.toastSubject.asObservable();

  show(message: string, duration: number,type:ToastType) {
    this.toastSubject.next({ message, duration,type });
  }
}
