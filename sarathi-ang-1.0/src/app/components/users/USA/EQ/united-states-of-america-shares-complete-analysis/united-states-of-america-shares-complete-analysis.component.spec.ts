import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UnitedStatesOfAmericaSharesCompleteAnalysisComponent } from './united-states-of-america-shares-complete-analysis.component';

describe('UnitedStatesOfAmericaSharesCompleteAnalysisComponent', () => {
  let component: UnitedStatesOfAmericaSharesCompleteAnalysisComponent;
  let fixture: ComponentFixture<UnitedStatesOfAmericaSharesCompleteAnalysisComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UnitedStatesOfAmericaSharesCompleteAnalysisComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UnitedStatesOfAmericaSharesCompleteAnalysisComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
