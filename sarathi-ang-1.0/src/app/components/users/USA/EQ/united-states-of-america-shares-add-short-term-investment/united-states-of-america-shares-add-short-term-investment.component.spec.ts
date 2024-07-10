import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UnitedStatesOfAmericaSharesAddShortTermInvestmentComponent } from './united-states-of-america-shares-add-short-term-investment.component';

describe('UnitedStatesOfAmericaSharesAddShortTermInvestmentComponent', () => {
  let component: UnitedStatesOfAmericaSharesAddShortTermInvestmentComponent;
  let fixture: ComponentFixture<UnitedStatesOfAmericaSharesAddShortTermInvestmentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UnitedStatesOfAmericaSharesAddShortTermInvestmentComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UnitedStatesOfAmericaSharesAddShortTermInvestmentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
