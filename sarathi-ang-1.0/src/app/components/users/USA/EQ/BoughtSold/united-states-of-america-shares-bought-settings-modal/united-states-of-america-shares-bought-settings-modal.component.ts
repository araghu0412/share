import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';

@Component({
  selector: 'app-united-states-of-america-shares-bought-settings-modal',
  templateUrl: './united-states-of-america-shares-bought-settings-modal.component.html',
  styleUrls: ['./united-states-of-america-shares-bought-settings-modal.component.css']
})
export class UnitedStatesOfAmericaSharesBoughtSettingsModalComponent implements OnInit {

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
