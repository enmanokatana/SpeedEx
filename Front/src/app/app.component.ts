import {Component, OnInit} from '@angular/core';
import { RouterOutlet } from '@angular/router';
import {NotificationsService} from "./core/services/notifications/notifications.service";

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent implements OnInit{
  title = 'SpeedEx';
  constructor(private notifications:NotificationsService) {
  }
  ngOnInit() {
    this.notifications.connect();
    setTimeout(()=>{
      this.notifications.sendMessage("uhm uhm")
    },2000)
  }

}

