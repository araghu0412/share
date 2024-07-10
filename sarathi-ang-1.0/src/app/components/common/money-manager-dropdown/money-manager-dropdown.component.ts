import { Component, EventEmitter, Input, OnChanges, OnInit, Output } from '@angular/core';

@Component({
  selector: 'app-money-manager-dropdown',
  templateUrl: './money-manager-dropdown.component.html',
  styleUrls: ['./money-manager-dropdown.component.css']
})
export class MoneyManagerDropdownComponent implements OnInit, OnChanges {

  @Input() disableDropdown!: boolean;
  @Input() resetDropdown!: boolean;
  @Input() placeholder: string = "Select here...";
  @Input() dropdownList!: Array<any>;
  @Input() tabindex: Number = 0;
  @Output("onDropdownSelect") dropdownSelectEventEmitter = new EventEmitter<any>();

  showDropdown: boolean = false;
  selectedValue: string = "";

  constructor() { }

  ngOnInit(): void {
    this.selectedValue = this.placeholder;
  }

  ngOnChanges(): void {
    if (this.resetDropdown) {
      this.selectedValue = this.placeholder;
    }
  }

  onDropdownClick() {
    if (!this.disableDropdown) {
      this.showDropdown = !this.showDropdown;
    }
  }

  onDropdownValueClick(dropdown: any) {
    this.selectedValue = dropdown.value;
    this.showDropdown = false;
    this.dropdownSelectEventEmitter.emit(dropdown);
  }

  onDropdownBlur() {
    this.showDropdown = false;
  }
}
