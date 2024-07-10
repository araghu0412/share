import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UnitedStatesOfAmericaSharesInvestmentResearchHeaderComponent } from './united-states-of-america-shares-investment-research-header.component';

describe('UnitedStatesOfAmericaSharesInvestmentResearchHeaderComponent', () => {
  let component: UnitedStatesOfAmericaSharesInvestmentResearchHeaderComponent;
  let fixture: ComponentFixture<UnitedStatesOfAmericaSharesInvestmentResearchHeaderComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UnitedStatesOfAmericaSharesInvestmentResearchHeaderComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UnitedStatesOfAmericaSharesInvestmentResearchHeaderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
