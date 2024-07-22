import {Component, OnDestroy, OnInit} from '@angular/core';
import {NgIf} from "@angular/common";

@Component({
  selector: 'app-chatbox',
  standalone: true,
  imports: [
    NgIf
  ],
  templateUrl: './chatbox.component.html',
  styleUrl: './chatbox.component.css'
})
export class ChatboxComponent implements OnInit,OnDestroy{
  open: boolean = false;
  myMessages:string[] = [];
  AiMessages:string[]=[];
  constructor() {
  }



  ngOnInit() {
  }

  ngOnDestroy() {
  }
}
