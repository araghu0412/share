import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MoneyManagerDropdownComponent } from './money-manager-dropdown.component';

describe('MoneyManagerDropdownComponent', () => {
  let component: MoneyManagerDropdownComponent;
  let fixture: ComponentFixture<MoneyManagerDropdownComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MoneyManagerDropdownComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MoneyManagerDropdownComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
