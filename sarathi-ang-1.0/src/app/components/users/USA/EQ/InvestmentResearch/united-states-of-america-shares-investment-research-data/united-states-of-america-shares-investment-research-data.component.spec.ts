import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UnitedStatesOfAmericaSharesInvestmentResearchDataComponent } from './united-states-of-america-shares-investment-research-data.component';

describe('UnitedStatesOfAmericaSharesInvestmentResearchDataComponent', () => {
  let component: UnitedStatesOfAmericaSharesInvestmentResearchDataComponent;
  let fixture: ComponentFixture<UnitedStatesOfAmericaSharesInvestmentResearchDataComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UnitedStatesOfAmericaSharesInvestmentResearchDataComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UnitedStatesOfAmericaSharesInvestmentResearchDataComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
