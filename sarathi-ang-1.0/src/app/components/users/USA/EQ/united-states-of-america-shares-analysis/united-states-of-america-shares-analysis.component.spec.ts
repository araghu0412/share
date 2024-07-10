import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UnitedStatesOfAmericaSharesAnalysisComponent } from './united-states-of-america-shares-analysis.component';

describe('UnitedStatesOfAmericaSharesAnalysisComponent', () => {
  let component: UnitedStatesOfAmericaSharesAnalysisComponent;
  let fixture: ComponentFixture<UnitedStatesOfAmericaSharesAnalysisComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UnitedStatesOfAmericaSharesAnalysisComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UnitedStatesOfAmericaSharesAnalysisComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
