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
  code :any = 'const x = 5;';
  output: string = '// Output will be displayed here...';

  updateLineNumbers(event: Event): void {
    const textArea = event.target as HTMLTextAreaElement;
    const lineNumbers = document.querySelector('.line-numbers') as HTMLElement;
    const lines = textArea.value.split('\n').length;
    lineNumbers.innerHTML = Array(lines).fill('<span></span>').join('');
  }

  syncScroll(event: Event): void {
    const textArea = event.target as HTMLTextAreaElement;
    const lineNumbers = document.querySelector('.line-numbers') as HTMLElement;
    lineNumbers.scrollTop = textArea.scrollTop;
  }

  runCode(): void {
    try {
      this.output = eval(this.code); // Caution: Do not use eval in production
    } catch (e:any) {
      this.output = e.message;
    }
  }
}
