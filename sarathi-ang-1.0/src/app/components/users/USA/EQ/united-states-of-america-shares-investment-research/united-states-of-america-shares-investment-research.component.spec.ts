import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UnitedStatesOfAmericaSharesInvestmentResearchComponent } from './united-states-of-america-shares-investment-research.component';

describe('UnitedStatesOfAmericaSharesInvestmentResearchComponent', () => {
  let component: UnitedStatesOfAmericaSharesInvestmentResearchComponent;
  let fixture: ComponentFixture<UnitedStatesOfAmericaSharesInvestmentResearchComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UnitedStatesOfAmericaSharesInvestmentResearchComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UnitedStatesOfAmericaSharesInvestmentResearchComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
