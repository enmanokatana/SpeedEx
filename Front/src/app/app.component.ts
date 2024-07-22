import {Component, OnDestroy, OnInit} from '@angular/core';
import {RouterOutlet} from '@angular/router';
import {NotificationsService} from "./core/services/notifications/notifications.service";
import {ToastComponent} from "./core/componenets/toast/toast.component";
import {ToastService} from "./core/services/toast/toast.service";
import {Subscription} from "rxjs";
import {ToastType} from "./core/enums/ToastType";
import jwt_decode from 'jwt-decode';
import {JwtTokenService} from "./core/services/JwtToken/jwt-token.service";
import {AuthService} from "./core/services/Auth/auth.service";

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
              private toastService:ToastService,
              private authService:AuthService) {
  }

  ngOnInit(): void {
    this.authService.logoutIfTokenExpired();
    setTimeout(()=> {
      this.notificationsService.connect();
      this.notificationsSubscription = this.notificationsService.subscribeToNotifications().subscribe(message => {
        this.toastService.show(message, 10000,ToastType.Info); // Show toast notification with the received message
      });
    },5000)

  }
  ngOnDestroy() {
    if (this.notificationsSubscription) {
      this.notificationsSubscription.unsubscribe();
    }
  }
}

