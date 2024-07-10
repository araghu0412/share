import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UnitedStatesOfAmericaSharesShortTermInvestmentDataComponent } from './united-states-of-america-shares-short-term-investment-data.component';

describe('UnitedStatesOfAmericaSharesShortTermInvestmentDataComponent', () => {
  let component: UnitedStatesOfAmericaSharesShortTermInvestmentDataComponent;
  let fixture: ComponentFixture<UnitedStatesOfAmericaSharesShortTermInvestmentDataComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UnitedStatesOfAmericaSharesShortTermInvestmentDataComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UnitedStatesOfAmericaSharesShortTermInvestmentDataComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
