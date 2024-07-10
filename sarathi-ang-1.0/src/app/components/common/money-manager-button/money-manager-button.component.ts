import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';

@Component({
  selector: 'app-money-manager-button',
  templateUrl: './money-manager-button.component.html',
  styleUrls: ['./money-manager-button.component.css']
})
export class MoneyManagerButtonComponent implements OnInit {

  @Input() buttonTitle!: string;
  @Input() buttonDisabled: boolean = false;
  @Input() buttonClass: string = "";
  @Output("onButtonClick") buttonClickEventEmitter = new EventEmitter();

  constructor() { }

  ngOnInit(): void {
  }

  onButtonClicked() {
    this.buttonClickEventEmitter.emit();
  }
}
