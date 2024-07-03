import {Component, OnDestroy, OnInit} from '@angular/core';
import { RouterOutlet } from '@angular/router';
import {NotificationsService} from "./core/services/notifications/notifications.service";
import {ToastComponent} from "./core/componenets/toast/toast.component";
import {ToastService} from "./core/services/toast/toast.service";
import {Subscription} from "rxjs";

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, ToastComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent implements OnInit,OnDestroy{
  title = 'SpeedEx';
  private notificationsSubscription!: Subscription;

  private notificationsArr: any[]= [];
  constructor(private notificationsService:NotificationsService,
              private toastService:ToastService) {
  }

  ngOnInit(): void {
    this.notificationsService.connect();

    this.notificationsSubscription = this.notificationsService.subscribeToNotifications().subscribe(message => {
      this.toastService.show(message, 10000); // Show toast notification with the received message
    });
  }
  showToast() {
    this.toastService.show('This is a toast message!', 3000);
  }
  ngOnDestroy() {
    if (this.notificationsSubscription) {
      this.notificationsSubscription.unsubscribe();
    }
  }
}

