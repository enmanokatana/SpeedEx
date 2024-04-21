import { Component } from '@angular/core';
import {HeaderComponent} from "../../../core/componenets/header/header.component";
import {RouterLink} from "@angular/router";

@Component({
  selector: 'app-workspace',
  standalone: true,
  imports: [
    HeaderComponent,
    RouterLink
  ],
  templateUrl: './workspace.component.html',
  styleUrl: './workspace.component.css'
})
export class WorkspaceComponent {

}
