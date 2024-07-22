import {Component, OnDestroy, OnInit} from '@angular/core';
import {ToastService} from "../../services/toast/toast.service";
import {NgClass, NgIf} from "@angular/common";
import {Subscription} from "rxjs";
import {ToastType} from "../../enums/ToastType";

@Component({
  selector: 'app-toast',
  standalone: true,
  imports: [
    NgIf,
    NgClass
  ],
  templateUrl: './toast.component.html',
  styleUrl: './toast.component.css'
})
export class ToastComponent implements OnInit,OnDestroy {
  showToast = false;
  message = '';
  timeout: any;
  type!: ToastType;
  progressMax: any;
  progressInterval: any;
  progressValue: number = 0;
  private toastSubscription!: Subscription;

  constructor(private toastService: ToastService) {
  }

  ngOnInit(): void {
    this.toastSubscription = this.toastService.toastState.subscribe((toast) => {
      this.message = toast.message;
      this.showToast = true;
      this.type = toast.type;

      // Clear existing timeout if any
      if (this.timeout) {
        clearTimeout(this.timeout);
      }

      // Set timeout to hide toast after duration
      this.timeout = setTimeout(() => {
        this.showToast = false;
        this.progressValue = 0; // Reset progress when toast hides
      }, toast.duration);

      // Set progress bar maximum to toast duration
      this.progressMax = toast.duration;

      // Start incrementing progress bar
      this.startProgressIncrement(toast.duration);
    });
  }

  ngOnDestroy(): void {
    // Unsubscribe from toastService
    if (this.toastSubscription) {
      this.toastSubscription.unsubscribe();
    }

    // Clear timeout and progress interval
    if (this.timeout) {
      clearTimeout(this.timeout);
    }
    if (this.progressInterval) {
      clearInterval(this.progressInterval);
    }
  }

  startProgressIncrement(duration: number): void {
    const incrementInterval = 100; // Interval in milliseconds to update progress (adjust as needed)
    const steps = duration / incrementInterval; // Number of steps to complete duration

   // console.log(`Starting progress increment: duration=${duration}ms, incrementInterval=${incrementInterval}ms, steps=${steps}`);

    // Clear existing interval if any
    if (this.progressInterval) {
      clearInterval(this.progressInterval);
     // console.log('Cleared existing progress interval');
    }

    // Start interval to increment progress
    this.progressInterval = setInterval(() => {
      // Increment progress value
      this.progressValue += (100 / steps);
      //console.log(`Progress updated: progressValue=${this.progressValue.toFixed(2)}%`);

      // Clear interval and reset progress if toast is hidden
      if (!this.showToast) {
        clearInterval(this.progressInterval);
        //console.log('Toast hidden, cleared progress interval');
        this.progressValue = 0;
      }
    }, incrementInterval);
  }

  closeToast(): void {
    // Close toast and clear timeout
    this.showToast = false;
    clearTimeout(this.timeout);
  }

  onGetStyle(): string {
    // Method to return dynamic styling based on toast type
    switch (this.type) {
      case ToastType.Success:
        return 'bg-green-500';
      case ToastType.Error:
        return 'bg-red-500';
      case ToastType.Warning:
        return 'bg-yellow-500';
      case ToastType.Info:
        return 'bg-grey-500';
      default:
        return "";
    }
  }

  onGetProgressStyle(): string {
    switch (this.type) {
      case ToastType.Success:
        return 'progress-success';
      case ToastType.Error:
        return 'progress-error';
      case ToastType.Warning:
        return 'progress-warning';
      case ToastType.Info:
        return 'progress-info';
      default:
        return "";
    }
  }
}
