import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UnitedStatesOfAmericaSharesOneShareShortTermInvestmentComponent } from './united-states-of-america-shares-one-share-short-term-investment.component';

describe('UnitedStatesOfAmericaSharesOneShareShortTermInvestmentComponent', () => {
  let component: UnitedStatesOfAmericaSharesOneShareShortTermInvestmentComponent;
  let fixture: ComponentFixture<UnitedStatesOfAmericaSharesOneShareShortTermInvestmentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UnitedStatesOfAmericaSharesOneShareShortTermInvestmentComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UnitedStatesOfAmericaSharesOneShareShortTermInvestmentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
