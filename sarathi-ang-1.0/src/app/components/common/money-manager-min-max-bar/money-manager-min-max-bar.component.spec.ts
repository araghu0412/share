import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MoneyManagerMinMaxBarComponent } from './money-manager-min-max-bar.component';

describe('MoneyManagerMinMaxBarComponent', () => {
  let component: MoneyManagerMinMaxBarComponent;
  let fixture: ComponentFixture<MoneyManagerMinMaxBarComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MoneyManagerMinMaxBarComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MoneyManagerMinMaxBarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
