import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UnitedStatesOfAmericaSharesShortTermInvestmentComponent } from './united-states-of-america-shares-short-term-investment.component';

describe('UnitedStatesOfAmericaSharesShortTermInvestmentComponent', () => {
  let component: UnitedStatesOfAmericaSharesShortTermInvestmentComponent;
  let fixture: ComponentFixture<UnitedStatesOfAmericaSharesShortTermInvestmentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UnitedStatesOfAmericaSharesShortTermInvestmentComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UnitedStatesOfAmericaSharesShortTermInvestmentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
