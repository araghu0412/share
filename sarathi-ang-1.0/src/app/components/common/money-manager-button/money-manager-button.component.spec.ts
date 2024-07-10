import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MoneyManagerButtonComponent } from './money-manager-button.component';

describe('MoneyManagerButtonComponent', () => {
  let component: MoneyManagerButtonComponent;
  let fixture: ComponentFixture<MoneyManagerButtonComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MoneyManagerButtonComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MoneyManagerButtonComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
