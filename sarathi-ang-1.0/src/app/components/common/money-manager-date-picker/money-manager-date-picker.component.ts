import { Component, EventEmitter, Input, OnInit, Output, SimpleChange, SimpleChanges } from '@angular/core';
import { getDefaultDate } from 'src/app/common/utils/utils';

const DATE_VIEW = {
  NONE: "NONE",
  DATE: "DATE",
  MONTH: "MONTH",
  YEAR: "YEAR"
}

@Component({
  selector: 'app-money-manager-date-picker',
  templateUrl: './money-manager-date-picker.component.html',
  styleUrls: ['./money-manager-date-picker.component.css']
})
export class MoneyManagerDatePickerComponent implements OnInit {

  @Input() tabindex: Number = 0;
  @Input() resetDatePicker!: boolean;
  @Input() defaultDate!: string;
  @Output("onSelectedFullDate") selectedFullDateEventEmitter = new EventEmitter<string> ();

  dayMap: Map<any, any> = new Map();
  monthMap: Map<any, any> = new Map();
  showDatePicker: boolean = false;
  datePickerContentView: any = DATE_VIEW.DATE;
  selectedFullDate: string = "";
  selectedDate: string = "";
  selectedMonth: string = "";
  selectedYear: string = "";
  displayDate: string = "";
  displayMonth: string = "";
  displayYear: string = "";
  displayStartYear: string = "";
  displayEndYear: string = "";
  calendarDaysList: Array<any> = [];
  calendarMonthsList: Array<any> = [];
  calendarYearsList: Array<any> =  [];

  constructor() { }

  ngOnInit(): void {
    this.dayMap.set(0, "Su");
    this.dayMap.set(1, "Mo");
    this.dayMap.set(2, "Tu");
    this.dayMap.set(3, "We");
    this.dayMap.set(4, "Th");
    this.dayMap.set(5, "Fr");
    this.dayMap.set(6, "Sa");

    this.monthMap.set(0, "JAN");
    this.monthMap.set(1, "FEB");
    this.monthMap.set(2, "MAR");
    this.monthMap.set(3, "APR");
    this.monthMap.set(4, "MAY");
    this.monthMap.set(5, "JUN");
    this.monthMap.set(6, "JUL");
    this.monthMap.set(7, "AUG");
    this.monthMap.set(8, "SEP");
    this.monthMap.set(9, "OCT");
    this.monthMap.set(10, "NOV");
    this.monthMap.set(11, "DEC");
    this.monthMap.set("JAN", 0);
    this.monthMap.set("FEB", 1);
    this.monthMap.set("MAR", 2);
    this.monthMap.set("APR", 3);
    this.monthMap.set("MAY", 4);
    this.monthMap.set("JUN", 5);
    this.monthMap.set("JUL", 6);
    this.monthMap.set("AUG", 7);
    this.monthMap.set("SEP", 8);
    this.monthMap.set("OCT", 9);
    this.monthMap.set("NOV", 10);
    this.monthMap.set("DEC", 11);

    let defaultDate = getDefaultDate(this.defaultDate);
    this.selectedDate = defaultDate.split("-")[0];
    this.selectedMonth = defaultDate.split("-")[1];
    this.selectedYear = defaultDate.split("-")[2];

    this.resetDisplayDateDetails();

    this.selectedFullDate = this.selectedDate + "-" + this.selectedMonth + "-" + this.selectedYear;
    this.onSelectedFullDateChange();

    // Months
    this.getMonthViewCalendar();

    // Year
    this.getYearViewCalendar();
  }

  ngOnChanges() {
    if (this.resetDatePicker) {
      if (this.defaultDate === this.selectedFullDate) {
        return;
      }

      let defaultDate = getDefaultDate(this.defaultDate);
      this.selectedDate = defaultDate.split("-")[0];
      this.selectedMonth = defaultDate.split("-")[1];
      this.selectedYear = defaultDate.split("-")[2];

      this.resetDisplayDateDetails();

      this.selectedFullDate = this.selectedDate + "-" + this.selectedMonth + "-" + this.selectedYear;
    }
  }

  onSelectedFullDateChange() {
    this.selectedFullDateEventEmitter.emit(this.selectedFullDate);
  }

  onDatePickerClick() {
    this.getDateViewCalendarDays(this.selectedMonth, this.selectedYear);
    this.resetDisplayDateDetails();
    this.showDatePicker = !this.showDatePicker;
  }

  onDatePickerBlur() {
    this.getDateViewCalendarDays(this.selectedMonth, this.selectedYear);
    this.resetDisplayDateDetails();
    this.showDatePicker = false;
  }

  getDateViewCalendarDays(month: any, year: any) {
    let startDay = new Date(year, this.monthMap.get(month), 1).getDay();
    let totalDaysInMonth = new Date(year, this.monthMap.get(month) + 1, 0).getDate();

    this.calendarDaysList = [];

    let weeklyDaysList = [];

    let id = 0;
    let weekId = 0;
    for (let i = 0; i < startDay; i++) {
      weeklyDaysList.push({id: id, value: ""});
      id++;
    }

    for (let i = 1; i <= totalDaysInMonth; i++) {
      if (id % 7 === 0) {
        this.calendarDaysList.push({weekId: weekId, weeklyDaysList: weeklyDaysList});
        weeklyDaysList = [];
        weekId++
      }
      weeklyDaysList.push({id: id, value: i.toString()});
      id++;
    }

    while(id%7 !== 0) {
      weeklyDaysList.push({id: id, value: ""});
      id++;
    }

    this.calendarDaysList.push({weekId: weekId, weeklyDaysList: weeklyDaysList});
  }

  resetDisplayDateDetails() {
    this.datePickerContentView = DATE_VIEW.DATE;
    this.displayDate = this.selectedDate;
    this.displayMonth = this.selectedMonth;
    this.displayYear = this.selectedYear;
  }

  onDateSelect(selectedDate: any) {
    this.selectedDate = selectedDate.date;
    this.selectedMonth = selectedDate.month;
    this.selectedYear = selectedDate.year;
    this.selectedFullDate = this.selectedDate + "-" + this.selectedMonth + "-" + this.selectedYear;
    this.onSelectedFullDateChange();
    this.resetDisplayDateDetails();
    this.showDatePicker = false;
  }

  onPreviousMonth() {
    let month = this.monthMap.get(this.displayMonth);
    let year = parseInt(this.displayYear);

    if (month === 0) {
      month = 11;
      year = year - 1;
    } else {
      month = month - 1;
    }

    this.displayMonth = this.monthMap.get(month);
    this.displayYear = year.toString();

    this.getDateViewCalendarDays(this.displayMonth, this.displayYear);
  }
  
  onNextMonth() {
    let month = this.monthMap.get(this.displayMonth);
    let year = parseInt(this.displayYear);

    if (month === 11) {
      month = 0;
      year = year + 1;
    } else {
      month = month + 1;
    }

    this.displayMonth = this.monthMap.get(month);
    this.displayYear = year.toString();

    this.getDateViewCalendarDays(this.displayMonth, this.displayYear);
  }

  onMonthClick() {
    this.datePickerContentView = DATE_VIEW.MONTH;
  }

  getMonthViewCalendar() {
    this.calendarMonthsList = [];

    let row = 0;
    let monthId = 0;
    for (let i = 0; i < 4; i++) {
      let monthList = [];
      for (let j = 0; j < 3; j++) {
        monthList.push({monthId: monthId, month: this.monthMap.get(monthId)});
        monthId++;
      }
      this.calendarMonthsList.push({rowId: row, monthList: monthList});
      row++;
    }
  }

  onMonthSelect(month: string) {
    this.displayMonth = month;
    
    this.getDateViewCalendarDays(this.displayMonth, this.displayYear);
    this.datePickerContentView = DATE_VIEW.DATE;
  }

  onYearClick() {
    this.getDisplayStartYearDisplayEndYear(parseInt(this.displayYear) - 7, parseInt(this.displayYear) + 7);
    this.getYearViewCalendar();
    this.datePickerContentView = DATE_VIEW.YEAR;
  }

  getDisplayStartYearDisplayEndYear(displayStartYear: number, displayEndYear: number) {
    this.displayStartYear = displayStartYear.toString();
    this.displayEndYear = displayEndYear.toString();
  }

  getYearViewCalendar() {
    this.calendarYearsList = [];
    let year = parseInt(this.displayStartYear);

    let row = 0;
    for (let i = 0; i < 5; i++) {
      let yearList = [];
      for (let j = 0; j < 3; j++) {
        yearList.push({yearId: year, year: year.toString()});
        year++;
      }
      this.calendarYearsList.push({rowId: row, yearList: yearList});
      row++;
    }
  }

  onPreviousYearList() {
    this.getDisplayStartYearDisplayEndYear(parseInt(this.displayStartYear) - 15, parseInt(this.displayStartYear) - 1);
    this.getYearViewCalendar();
  }

  onNextYearList() {
    this.getDisplayStartYearDisplayEndYear(parseInt(this.displayEndYear) + 1, parseInt(this.displayEndYear) + 15);
    this.getYearViewCalendar();
  }

  onYearSelect(year: string) {
    this.displayYear = year;
    this.getDateViewCalendarDays(this.displayMonth, this.displayYear);
    this.datePickerContentView = DATE_VIEW.DATE;
  }
  
}
