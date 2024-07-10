import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';

@Component({
  selector: 'app-bhaaratha-shares-bought-settings-modal',
  templateUrl: './bhaaratha-shares-bought-settings-modal.component.html',
  styleUrls: ['./bhaaratha-shares-bought-settings-modal.component.css']
})
export class BhaarathaSharesBoughtSettingsModalComponent implements OnInit {

  @Input() longTermRadioList!: Array<any>;
  @Input() longTermRadioDefault!: any;
  @Output("onCloseSettingsClick") closeSettingsClickEventEmitter = new EventEmitter<any>(); 
  @Output("onConfirmSettingsClick") confirmSettingsClickEventEmitter = new EventEmitter<any>();

  longTermRadio: any;

  constructor() { }

  ngOnInit(): void {
    this.longTermRadio = this.longTermRadioDefault;
  }

  onLongTermRadioSelect(longTermRadioSelect: any) {
    this.longTermRadio = longTermRadioSelect;
  }

  onCloseSettingsClick() {
    this.closeSettingsClickEventEmitter.emit();
  }

  onConfirmSettingsClick() {
    this.confirmSettingsClickEventEmitter.emit(this.longTermRadio);
  }

}
