import { Directive, ElementRef, Input, Renderer2 } from '@angular/core';

@Directive({
  selector: '[appHighlightByNumbers]'
})
export class HighlightByNumbersDirective {

  @Input() fontWeight: string = '';
  @Input() value!: string;

  constructor(
    private el: ElementRef,
    private renderer: Renderer2
  ) { }

  ngOnInit() {
    let htmlValue: any = this.value === null ? this.value : this.value.split('%')[0];

    this.renderer.setStyle(this.el.nativeElement, 'font-weight', this.fontWeight);

    if (htmlValue > 0) {
      this.renderer.setStyle(this.el.nativeElement, 'color', 'green');
    } else if (htmlValue < 0) {
      this.renderer.setStyle(this.el.nativeElement, 'color', 'red');
    } else if (htmlValue == 0) {
      this.renderer.setStyle(this.el.nativeElement, 'color', 'darkgoldenrod');
    }

  }

}
