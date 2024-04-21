import { Routes } from '@angular/router';
import { LoginComponent } from './features/pages/login/login.component';
import { HomeComponent } from './features/pages/home/home.component';
import { RegisterComponent } from './features/pages/register/register.component';
import { PageNotFoundComponent } from './features/pages/page-not-found/page-not-found.component';
import { VideoCallComponent } from './features/pages/video-call/video-call.component';
import {ProfileComponent} from "./features/pages/profile/profile.component";
import {CreateExamComponent} from "./features/pages/create-exam/create-exam.component";
import {WorkspaceComponent} from "./features/pages/workspace/workspace.component";

export const routes: Routes = [
  { path: '', pathMatch: 'full', redirectTo: 'Home' },
  { path: 'Login', component: LoginComponent, title: 'Login' },
  { path: 'Home', component: HomeComponent, title: 'Home' },
  { path: 'Register', component: RegisterComponent, title: 'Register' },
  { path: 'Call', title: 'Call', component: VideoCallComponent },
  {path:'Profile',title:'Profile',component:ProfileComponent},
  {path:'Create',title:'Create new exam', component:CreateExamComponent},
  {path:'Workspace',title:'Workspace',component:WorkspaceComponent},
  { path: '**', pathMatch: 'full', component: PageNotFoundComponent },
];
