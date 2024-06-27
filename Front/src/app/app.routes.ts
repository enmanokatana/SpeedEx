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
import {InvitationsComponent} from "./features/pages/invitations/invitations.component";
import {WelcomeComponent} from "./features/pages/welcome/welcome.component";
import {ConnectionlostComponent} from "./features/pages/connectionlost/connectionlost.component";
import {UserGuard} from "./core/Guards/user.guard";
import {UsersComponent} from "./features/components/users/users.component";
import {MyExamsComponent} from "./features/components/my-exams/my-exams.component";
import {DocumentationComponent} from "./features/components/documentation/documentation.component";
import {ConsultComponent} from "./features/pages/consult/consult.component";
import {UsersResultsComponent} from "./features/components/users-results/users-results.component";
import {ExamDetailsComponent} from "./features/components/exam-details/exam-details.component";
import {MainComponent} from "./features/components/main/main.component";
import {SettingsComponent} from "./features/components/settings/settings.component";

export const routes: Routes = [

  { path: '',
    pathMatch: 'full',
    redirectTo: 'Welcome' },

  { path: 'Login',
    component: LoginComponent,
    title: 'Login' },

  { path: 'Home',
    component: HomeComponent,
    canActivate:[AuthGuard],
    title: 'Home',
    children:[
      {path: 'main',component:MainComponent ,title:'main'},
      {path: 'settings',component: SettingsComponent,title:'Settings'},
      {path: '',redirectTo: 'main',pathMatch: "full"}
    ]

  },

  { path: 'Register',
    component: RegisterComponent,
    title: 'Register' },

  {
    path:'Consult/:id',
    component:ConsultComponent,
    title:'Consult',
    children:[
      {path: 'users',component: UsersResultsComponent},
      {path: 'detail/:examId',component:ExamDetailsComponent},
      {path: '',redirectTo: 'users',pathMatch: "full"}
    ]
  },


  {path:'Profile',
    title:'Profile',
    component:ProfileComponent},

  {path:'Create/:id',
    title:'Create new exam',
    canActivate:[AdminGuard],
    component:CreateExamComponent},

  { path:'Workspace/:id',
    title:'Workspace',
    component:WorkspaceComponent,
    children:[
      {path:'users',component:UsersComponent},
      {path:'exams',component:MyExamsComponent},
      {path:'docs',component:DocumentationComponent},
      {path:'',redirectTo:'exams',pathMatch:"full"}
  ]
  },

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
