import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MoneyManagerDatePickerComponent } from './money-manager-date-picker.component';

describe('MoneyManagerDatePickerComponent', () => {
  let component: MoneyManagerDatePickerComponent;
  let fixture: ComponentFixture<MoneyManagerDatePickerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MoneyManagerDatePickerComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MoneyManagerDatePickerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
