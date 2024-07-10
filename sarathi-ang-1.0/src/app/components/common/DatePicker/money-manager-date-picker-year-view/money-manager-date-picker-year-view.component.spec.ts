import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MoneyManagerDatePickerYearViewComponent } from './money-manager-date-picker-year-view.component';

describe('MoneyManagerDatePickerYearViewComponent', () => {
  let component: MoneyManagerDatePickerYearViewComponent;
  let fixture: ComponentFixture<MoneyManagerDatePickerYearViewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MoneyManagerDatePickerYearViewComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MoneyManagerDatePickerYearViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
