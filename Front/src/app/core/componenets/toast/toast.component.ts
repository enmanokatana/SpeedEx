import {Component, OnDestroy, OnInit} from '@angular/core';
import {ToastService} from "../../services/toast/toast.service";
import {NgIf} from "@angular/common";
import {Subscription} from "rxjs";

@Component({
  selector: 'app-toast',
  standalone: true,
  imports: [
    NgIf
  ],
  templateUrl: './toast.component.html',
  styleUrl: './toast.component.css'
})
export class ToastComponent implements OnInit,OnDestroy{
  showToast = false;
  message = '';
  timeout: any;
  private toastSubscription!: Subscription;

  constructor(private toastService: ToastService) {}

  ngOnInit(): void {
    this.toastSubscription = this.toastService.toastState.subscribe((toast) => {
      this.message = toast.message;
      this.showToast = true;

      if (this.timeout) {
        clearTimeout(this.timeout);
      }

      this.timeout = setTimeout(() => {
        this.showToast = false;
      }, toast.duration);
    });
  }

  ngOnDestroy(): void {
    if (this.toastSubscription) {
      this.toastSubscription.unsubscribe();
    }
    if (this.timeout) {
      clearTimeout(this.timeout);
    }
  }

  closeToast() {
    this.showToast = false;
    clearTimeout(this.timeout);
  }
}
