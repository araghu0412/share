import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';

@Component({
  selector: 'app-money-manager-date-picker-year-view',
  templateUrl: './money-manager-date-picker-year-view.component.html',
  styleUrls: ['./money-manager-date-picker-year-view.component.css']
})
export class MoneyManagerDatePickerYearViewComponent implements OnInit {

  @Input() displayYear!: string;
  @Input() calendarYearsList!: Array<any>;
  @Input() displayStartYear!: string;
  @Input() displayEndYear!: string;
  @Output("onPreviousYearList") previousYearListEventEmitter = new EventEmitter<any>();
  @Output("onNextYearList") nextYearListEventEmitter = new EventEmitter<any>();
  @Output("onYearSelect") yearSelectEventEmitter = new EventEmitter<string>();

  constructor() { }

  ngOnInit(): void {
  }

  onPreviousYearListClick() {
    this.previousYearListEventEmitter.emit();
  }

  onNextYearListClick() {
    this.nextYearListEventEmitter.emit();
  }

  onYearSelect(year: string) {
    this.yearSelectEventEmitter.emit(year);
  }

}
