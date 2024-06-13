import { Component } from '@angular/core';
import {TypeWriterDirective} from "../../../core/Directives/app-type-writer.directive";
import {RouterLink} from "@angular/router";

@Component({
  selector: 'app-welcome',
  standalone: true,
  imports: [
    TypeWriterDirective,
    RouterLink
  ],
  templateUrl: './welcome.component.html',
  styleUrl: './welcome.component.css'
})
export class WelcomeComponent {

}
