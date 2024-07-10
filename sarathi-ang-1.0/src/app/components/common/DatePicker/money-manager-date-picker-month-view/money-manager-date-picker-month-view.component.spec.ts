import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MoneyManagerDatePickerMonthViewComponent } from './money-manager-date-picker-month-view.component';

describe('MoneyManagerDatePickerMonthViewComponent', () => {
  let component: MoneyManagerDatePickerMonthViewComponent;
  let fixture: ComponentFixture<MoneyManagerDatePickerMonthViewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MoneyManagerDatePickerMonthViewComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MoneyManagerDatePickerMonthViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
