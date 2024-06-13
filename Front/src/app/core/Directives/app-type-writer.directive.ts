import { Directive, ElementRef, OnInit } from '@angular/core';

@Directive({
  selector: '[appTypeWriter]',
  standalone: true,

})
export class TypeWriterDirective implements OnInit {
  constructor(private element: ElementRef) {}

  ngOnInit(): void {
    const text = this.element.nativeElement.textContent.trim();
    this.element.nativeElement.textContent = '';
    let index = 0;
    const interval = setInterval(() => {
      this.element.nativeElement.textContent += text[index];
      index++;
      if (index === text.length) {
        clearInterval(interval);
      }
    }, 100);
  }
}
