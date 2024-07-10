import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UnitedStatesOfAmericaSharesDividendYieldAnalysisComponent } from './united-states-of-america-shares-dividend-yield-analysis.component';

describe('UnitedStatesOfAmericaSharesDividendYieldAnalysisComponent', () => {
  let component: UnitedStatesOfAmericaSharesDividendYieldAnalysisComponent;
  let fixture: ComponentFixture<UnitedStatesOfAmericaSharesDividendYieldAnalysisComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UnitedStatesOfAmericaSharesDividendYieldAnalysisComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UnitedStatesOfAmericaSharesDividendYieldAnalysisComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
