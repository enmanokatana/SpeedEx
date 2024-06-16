import { Component } from '@angular/core';
import {FormsModule} from "@angular/forms";
import {Highlight, HighlightAuto} from "ngx-highlightjs";
import {HighlightLineNumbers} from "ngx-highlightjs/line-numbers";

@Component({
  selector: 'app-code-input',
  standalone: true,
  imports: [
    FormsModule,
    Highlight,
    HighlightAuto,
    HighlightLineNumbers
  ],
  templateUrl: './code-input.component.html',
  styleUrl: './code-input.component.css'
})
export class CodeInputComponent {

}
