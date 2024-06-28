import { Injectable } from '@angular/core';
import * as Stomp from 'stompjs'
import SockJS from 'sockjs-client';
import {environment} from "../../../../environmenets/environment";
@Injectable({
  providedIn: 'root'
})
export class NotificationsService {

  private url = environment.API_BASE_URL;

  private stompClient: any;
  private connected: boolean = false;

  constructor() { }

  connect() {
    const socket = new SockJS('http://localhost:8080/ws');
    this.stompClient = Stomp.over(socket);

    this.stompClient.connect({}, (frame: any) => {
      console.log('Connected: ' + frame);
      this.connected = true;
      this.stompClient.subscribe('/topic/notifications', (notification: any) => {
        var message = JSON.parse(notification.body);
        console.log(message);
      });
    }, (error: any) => {
      console.error('Connection error: ' + error);
      this.connected = false;
    });
  }

  sendMessage(message: string) {
    if (this.connected) {
      console.log('Sending message: ' + message);
      this.stompClient.send('/app/sendNotification', {}, message);
    } else {
      console.error('Unable to send message. Not connected.');
    }
  }
}
