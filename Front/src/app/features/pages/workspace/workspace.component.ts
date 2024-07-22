import {Component, HostListener, OnInit} from '@angular/core';
import {HeaderComponent} from "../../../core/componenets/header/header.component";
import {ActivatedRoute, NavigationEnd, Router, RouterLink, RouterOutlet} from "@angular/router";
import {FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators} from "@angular/forms";
import {UserService} from "../../../core/services/User/user.service";
import {WorkspaceService} from "../../../core/services/workspace/workspace.service";
import {NgClass, NgForOf, NgIf} from "@angular/common";
import {RightClickDirective} from "../../../core/Directives/right-click.directive";
import {filter} from "rxjs";

@Component({
  selector: 'app-workspace',
  standalone: true,
  imports: [
    HeaderComponent,
    RouterLink,
    ReactiveFormsModule,
    NgForOf,
    NgIf,
    FormsModule,
    RightClickDirective,
    RouterOutlet,
    NgClass,


  ],
  templateUrl: './workspace.component.html',
  styleUrl: './workspace.component.css'
})
export class WorkspaceComponent implements OnInit{

  constructor(private userService:UserService,
              private workspaceService:WorkspaceService,
              private route:ActivatedRoute,
              private router:Router,
              private builder: FormBuilder) {

  }


  email : string ="";
  WorkSpaceAdmin!:any;
  WorkSpaceUsers:any[] = [];
  id:any;
  exams:any[]=[];
  user!:any;
  user2:any = {
    id:0,
    name:'',
    firstname:'',
    lastname:''
  };
  loading:boolean = true;
  workspaces:any;
  emailForm!:FormGroup;
  image:string = '';
 currentChild:string = 'exams';
  ngOnInit() {
    this.id = this.route.snapshot.paramMap.get('id')
    this.router.events.subscribe(event=> {
      if (event instanceof NavigationEnd){
        const  url = this.router.url;
        if (url.includes('exams')){
          this.currentChild = 'exams'
        }else if (url.includes('users')){
          this.currentChild = 'users'
        }else if (url.includes('docs')){
          this.currentChild = 'docs'
        }
      }
    })
    this.onGetMyWorkspaces();



  }




  onGetWsImage(){
  this.workspaceService.getImage(this.id).subscribe({
    next:(response:any)=>{
      //console.log(response);
      this.image = response.result;
      },
    error:(e)=>{
      //console.log(e)
    },
    complete:()=>{

    }
  })
}

  ongGetWorkSpaceUsers(id:any){
  this.workspaceService.getWorkSpaceUsersIds(id).subscribe({
    next:(response)=>{
      this.onLoadUsers(response.result);
    },
    error:(e)=>{
      //console.log(e)
    },
    complete:()=>{
      //console.log(this.WorkSpaceUsers);
    }
  });
  }
  onGetMyWorkspaces() {
    this.workspaceService.getWorkSpaces(localStorage.getItem('id')).subscribe({
      next:(response)=>{
        //console.log(response);
       console.log('wss',response);
        this.workspaces=response.result;


      },
      error:(e)=>{
        //console.log(e);

      },
      complete:()=>{
        //console.log("Completed getting WSS")
        }
    })
  }
  navigate(id:number){
    this.router.navigate(['/Workspace',id]).then(()=> {
    window.location.reload();
    });
  }

  onLoadUsers(ids:any){
    for (let id of ids) {
      this.userService.getUserDto(id).subscribe({
        next:(response)=>{
          //console.log("USERS: ,",response);
          this.user2.id = id;
          this.user2.lastname = response.result.lastname;
          this.WorkSpaceUsers.push(response.result);

        }
      })
    }
  }



  protected readonly localStorage = localStorage;
  drawerCheckboxId: string = 'my-drawer-2';
  hidden: boolean = true;

  @HostListener('window:resize',['$event'])
  onResize(event:Event){
    this.adjustFieldValue(window.innerWidth);
  }
  private adjustFieldValue(windowWidth : number){
    if (windowWidth< 1400){
      this.hidden = false;
    }else {
      this.hidden = true;
    }
  }

  onGotoWs(id:any) {
    this.router.navigate(['/Workspace',id]);

  }
}
