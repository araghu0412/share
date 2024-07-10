import { KeyValue } from '@angular/common';
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';

@Component({
  selector: 'app-money-manager-date-picker-date-view',
  templateUrl: './money-manager-date-picker-date-view.component.html',
  styleUrls: ['./money-manager-date-picker-date-view.component.css']
})
export class MoneyManagerDatePickerDateViewComponent implements OnInit {

  @Input() dayMap!: Map<any, any>;
  @Input() displayMonth!: string;
  @Input() displayYear!: string;
  @Input() calendarDaysList!: Array<any>;
  @Input() selectedDate!: string;
  @Input() selectedMonth!: string;
  @Input() selectedYear!: string;
  @Output("onDateSelect") dateSelectEventEmitter = new EventEmitter<any>();
  @Output("onPreviousMonth") previousMonthEventEmitter = new EventEmitter<any>();
  @Output("onNextMonth") nextMonthEventEmitter = new EventEmitter<any>();
  @Output("onMonthClick") monthClickEventEmitter = new EventEmitter<any>();
  @Output("onYearClick") yearClickEventEmitter = new EventEmitter<any>();

  constructor() { }

  ngOnInit(): void {
  }

  onDateClick(date: string) {
    if (date !== "") {
      this.dateSelectEventEmitter.emit({date: date, month: this.displayMonth, year: this.displayYear});
    }
  }

  onPreviousMonthClick() {
    this.previousMonthEventEmitter.emit();
  }

  onNextMonthClick() {
    this.nextMonthEventEmitter.emit();
  }

  onMonthClick() {
    this.monthClickEventEmitter.emit();
  }

  onYearClick() {
    this.yearClickEventEmitter.emit();
  }

  originalOrder = (a: KeyValue<string, any>, b: KeyValue<string, any>): number => {
    return 0;
  }
}
