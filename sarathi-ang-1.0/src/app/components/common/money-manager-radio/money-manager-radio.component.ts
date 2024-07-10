import { Component, EventEmitter, Input, OnChanges, OnInit, Output } from '@angular/core';

@Component({
  selector: 'app-money-manager-radio',
  templateUrl: './money-manager-radio.component.html',
  styleUrls: ['./money-manager-radio.component.css']
})
export class MoneyManagerRadioComponent implements OnInit, OnChanges {

  @Input() resetRadio!: boolean;
  @Input() radioList!: Array<any>;
  @Input() radioDefault: any;
  @Input() radioName!: string;
  @Output("onRadioSelect") radioSelectEventEmitter = new EventEmitter<string>();

  radioSelected: any = {};

  constructor() { }

  ngOnInit(): void {
    if (this.radioDefault) {
      this.radioSelected = this.radioDefault;
    } else if (!this.radioDefault) {
      this.radioSelected = {};
    }
  }

  ngOnChanges() {
    if (this.resetRadio) {
      if (this.radioDefault) {
        this.radioSelected = this.radioDefault;
      } else if (!this.radioDefault) {
        this.radioSelected = {};
      }
    }
  }

  onRadioSelect() {
    this.radioSelectEventEmitter.emit(this.radioSelected);
  }
}
