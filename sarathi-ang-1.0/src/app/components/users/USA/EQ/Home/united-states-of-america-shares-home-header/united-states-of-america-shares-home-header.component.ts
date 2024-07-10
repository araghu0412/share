import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';

@Component({
  selector: 'app-united-states-of-america-shares-home-header',
  templateUrl: './united-states-of-america-shares-home-header.component.html',
  styleUrls: ['./united-states-of-america-shares-home-header.component.css']
})
export class UnitedStatesOfAmericaSharesHomeHeaderComponent implements OnInit {

  @Input() otherServicesDropdownResetDropdown!: boolean;
  @Input() otherServicesDropdownList!: Array<any>;
  @Output("onOtherServicesDropdownSelect") onOtherServicesDropdownSelectEventEmitter = new EventEmitter<any>();

  constructor() { }

  ngOnInit(): void {
  }

  onOtherServicesDropdownSelect(dropdownServicesSelected: any) {
    this.onOtherServicesDropdownSelectEventEmitter.emit(dropdownServicesSelected);
  }

}
