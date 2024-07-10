import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';

@Component({
  selector: 'app-money-manager-date-picker-month-view',
  templateUrl: './money-manager-date-picker-month-view.component.html',
  styleUrls: ['./money-manager-date-picker-month-view.component.css']
})
export class MoneyManagerDatePickerMonthViewComponent implements OnInit {

  @Input() calendarMonthsList!: Array<any>;
  @Input() displayMonth!: string;
  @Output("onMonthSelect") monthSelectEventEmitter = new EventEmitter<string>();

  constructor() { }

  ngOnInit(): void {
  }

  onMonthSelect(month: string) {
    this.monthSelectEventEmitter.emit(month);
  }
}
