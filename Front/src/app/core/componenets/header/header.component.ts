import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router, RouterLink} from "@angular/router";
import {NgIf} from "@angular/common";
import {StoreService} from "../../services/store/store.service";
import {AuthService} from "../../services/Auth/auth.service";
import {routes} from "../../../app.routes";

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
  path:string="";
  constructor(private store:StoreService,
              private authService:AuthService,
              private router:Router,
              private route:ActivatedRoute) {
  }

  onLogout(){
    this.authService.logout();
    this.router.navigate(['Login']);


  }
  ngOnInit(): void {
    this.profilepic = localStorage.getItem('image');
    this.path = this.route.snapshot.url[0].path;
  }

  protected readonly localStorage = localStorage;
}
