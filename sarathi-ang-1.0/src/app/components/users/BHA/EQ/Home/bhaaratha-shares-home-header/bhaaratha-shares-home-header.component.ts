import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';

@Component({
  selector: 'app-bhaaratha-shares-home-header',
  templateUrl: './bhaaratha-shares-home-header.component.html',
  styleUrls: ['./bhaaratha-shares-home-header.component.css']
})
export class BhaarathaSharesHomeHeaderComponent implements OnInit {

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
