import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MoneyManagerDatePickerDateViewComponent } from './money-manager-date-picker-date-view.component';

describe('MoneyManagerDatePickerDateViewComponent', () => {
  let component: MoneyManagerDatePickerDateViewComponent;
  let fixture: ComponentFixture<MoneyManagerDatePickerDateViewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MoneyManagerDatePickerDateViewComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MoneyManagerDatePickerDateViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
