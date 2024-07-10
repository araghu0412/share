import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UnitedStatesOfAmericaSharesShortTermInvestmentHeaderComponent } from './united-states-of-america-shares-short-term-investment-header.component';

describe('UnitedStatesOfAmericaSharesShortTermInvestmentHeaderComponent', () => {
  let component: UnitedStatesOfAmericaSharesShortTermInvestmentHeaderComponent;
  let fixture: ComponentFixture<UnitedStatesOfAmericaSharesShortTermInvestmentHeaderComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UnitedStatesOfAmericaSharesShortTermInvestmentHeaderComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UnitedStatesOfAmericaSharesShortTermInvestmentHeaderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
