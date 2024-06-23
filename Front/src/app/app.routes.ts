import { Routes } from '@angular/router';
import { LoginComponent } from './features/pages/login/login.component';
import { HomeComponent } from './features/pages/home/home.component';
import { RegisterComponent } from './features/pages/register/register.component';
import { PageNotFoundComponent } from './features/pages/page-not-found/page-not-found.component';
import {ProfileComponent} from "./features/pages/profile/profile.component";
import {CreateExamComponent} from "./features/pages/create-exam/create-exam.component";
import {WorkspaceComponent} from "./features/pages/workspace/workspace.component";
import {CreateWorkspaceComponent} from "./features/pages/create-workspace/create-workspace.component";
import {AuthGuard} from "./core/Guards/auth.guard";
import {AdminGuard} from "./core/Guards/admin.guard";
import {ExamComponent} from "./features/pages/exam/exam.component";
import {CodeInputComponent} from "./shared/code-input/code-input.component";
import {InvitationsComponent} from "./features/pages/invitations/invitations.component";
import {WelcomeComponent} from "./features/pages/welcome/welcome.component";
import {ConnectionlostComponent} from "./features/pages/connectionlost/connectionlost.component";
import {UserGuard} from "./core/Guards/user.guard";

export const routes: Routes = [

  { path: '',
    pathMatch: 'full',
    redirectTo: 'Welcome' },
  {
    path: 'Code',
    component: CodeInputComponent,
    title: 'code'
  },
  { path: 'Login',
    component: LoginComponent,
    title: 'Login' },

  { path: 'Home',
    component: HomeComponent,
    canActivate:[AuthGuard],
    title: 'Home' },

  { path: 'Register',
    component: RegisterComponent,
    title: 'Register' },



  {path:'Profile',
    title:'Profile',
    component:ProfileComponent},

  {path:'Create/:id',
    title:'Create new exam',
    canActivate:[AdminGuard],
    component:CreateExamComponent},

  {path:'Workspace/:id',
    title:'Workspace',
    component:WorkspaceComponent},

  {path:'CWS/:id',
    title:'CreateWorkspace',
    canActivate:[AdminGuard],
    component:CreateWorkspaceComponent},
  {path:'Exam/:id',
    title:'CreateWorkspace',
    canActivate:[AuthGuard],
    component:ExamComponent},
  {path:'Invitations',
    title:'Invites',
    canActivate:[AuthGuard,UserGuard],
    component:InvitationsComponent},
  {path:'Welcome',
    title:'Welcome',
    component:WelcomeComponent},
{path:'ConnectionLost',
    title:'Connection Lost',
    component:ConnectionlostComponent},

  { path: '**',
    pathMatch: 'full',
    component: PageNotFoundComponent },

];
