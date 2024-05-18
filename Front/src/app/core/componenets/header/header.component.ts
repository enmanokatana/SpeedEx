import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router, RouterLink} from "@angular/router";
import {NgIf} from "@angular/common";
import {StoreService} from "../../services/store/store.service";
import {AuthService} from "../../services/Auth/auth.service";
import {routes} from "../../../app.routes";
import {WorkspaceService} from "../../services/workspace/workspace.service";

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
  profilepic : any = null;
  path:string="";
  constructor(private store:StoreService,
              private authService:AuthService,
              private router:Router,
              private route:ActivatedRoute,
              private workspaceService:WorkspaceService) {
  }

  onLogout(){
    this.authService.logout();
    this.router.navigate(['Login']);


  }
  ngOnInit(): void {
    this.profilepic = localStorage.getItem('image');
    this.path = this.route.snapshot.url[0].path;
    this.onGetInvites();
  }

  onGetInvites(){
    this.workspaceService.getAllInvites(localStorage.getItem('id')).subscribe({
      next:(res)=>{
        console.log(res);
      }
    })

  }



  protected readonly localStorage = localStorage;
}
