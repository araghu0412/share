import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MoneyManagerRadioComponent } from './money-manager-radio.component';

describe('MoneyManagerRadioComponent', () => {
  let component: MoneyManagerRadioComponent;
  let fixture: ComponentFixture<MoneyManagerRadioComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MoneyManagerRadioComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MoneyManagerRadioComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
