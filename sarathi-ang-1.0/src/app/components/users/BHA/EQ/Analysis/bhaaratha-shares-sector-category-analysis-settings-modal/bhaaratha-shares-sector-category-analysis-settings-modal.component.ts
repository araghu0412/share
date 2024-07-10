import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';

@Component({
  selector: 'app-bhaaratha-shares-sector-category-analysis-settings-modal',
  templateUrl: './bhaaratha-shares-sector-category-analysis-settings-modal.component.html',
  styleUrls: ['./bhaaratha-shares-sector-category-analysis-settings-modal.component.css']
})
export class BhaarathaSharesSectorCategoryAnalysisSettingsModalComponent implements OnInit {

  @Input() longTermRadioList!: Array<any>;
  @Input() longTermRadioDefault!: any;
  @Input() valueInPercentageRadioList!: Array<any>;
  @Input() valueInPercentageRadioDefault!: any;
  @Output("onCloseSettingsClick") onCloseSettingsClickEventEmitter = new EventEmitter();
  @Output("onConfirmSettingsClick") onConfirmSettingsClickEventEmitter = new EventEmitter<any>();

  longTermRadio: any = {};
  valueInPercentageRadio: any = {};

  constructor() { }

  ngOnInit(): void {
    this.longTermRadio = this.longTermRadioDefault;
    this.valueInPercentageRadio = this.valueInPercentageRadioDefault;
  }

  onLongTermRadioSelect(longTermRadioSelect: any) {
    this.longTermRadio = longTermRadioSelect;
  }

  onValueInPercentageRadioSelect(valueInPercentageSelect: any) {
    this.valueInPercentageRadio = valueInPercentageSelect;
  }

  onCloseSettingsClick() {
    this.onCloseSettingsClickEventEmitter.emit();
  }

  onConfirmSettingsClick() {
    this.onConfirmSettingsClickEventEmitter.emit({ longTermRadio: this.longTermRadio, valueInPercentageRadio: this.valueInPercentageRadio});
  }

}
