import {Component, OnInit} from '@angular/core';
import {RouterLink} from "@angular/router";
import {NgIf} from "@angular/common";

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [
    RouterLink,
    NgIf
  ],
  templateUrl: './header.component.html',
  styleUrl: './header.component.css'
})
export class HeaderComponent implements OnInit{
  profilepic !: any;
  ngOnInit(): void {
    this.profilepic = localStorage.getItem('image');
  }

}
