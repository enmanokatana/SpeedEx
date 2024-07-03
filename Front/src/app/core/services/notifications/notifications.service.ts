import { Injectable } from '@angular/core';
import * as Stomp from 'stompjs'
import SockJS from 'sockjs-client';
import {environment} from "../../../../environmenets/environment";
import {Observable, Subject} from "rxjs";
@Injectable({
  providedIn: 'root'
})
export class NotificationsService {

  private url = environment.API_BASE_URL;

  private stompClient: any;
  private connected: boolean = false;
  private notificationSubject: Subject<any> = new Subject<any>();

  constructor() { }

  connect() {
    const socket = new SockJS('http://localhost:8080/ws');
    this.stompClient = Stomp.over(socket);
    this.stompClient.debug = () => {};// to stop logging

    this.stompClient.connect({}, (frame: any) => {
     // console.log('Connected: ' + frame);
      this.connected = true;
      this.stompClient.subscribe('/topic/notifications', (notification: any) => {
        this.notificationSubject.next(notification.body);
      });
      if (localStorage.getItem('role') !== 'ADMIN'){
        this.stompClient.subscribe(`/user/${localStorage.getItem('id')}/topic/notifications`, (notification: any) => {
        this.notificationSubject.next(notification.body);
      });}


    }, (error: any) => {
      console.error('Connection error: ' + error);
      this.connected = false;
    });
  }


  subscribeToNotifications():Observable<any>{
    return this.notificationSubject.asObservable();
  }
}
