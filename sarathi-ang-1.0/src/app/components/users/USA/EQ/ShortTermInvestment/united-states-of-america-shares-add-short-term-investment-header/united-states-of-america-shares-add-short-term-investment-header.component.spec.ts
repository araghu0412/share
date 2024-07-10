import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UnitedStatesOfAmericaSharesAddShortTermInvestmentHeaderComponent } from './united-states-of-america-shares-add-short-term-investment-header.component';

describe('UnitedStatesOfAmericaSharesAddShortTermInvestmentHeaderComponent', () => {
  let component: UnitedStatesOfAmericaSharesAddShortTermInvestmentHeaderComponent;
  let fixture: ComponentFixture<UnitedStatesOfAmericaSharesAddShortTermInvestmentHeaderComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UnitedStatesOfAmericaSharesAddShortTermInvestmentHeaderComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UnitedStatesOfAmericaSharesAddShortTermInvestmentHeaderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
