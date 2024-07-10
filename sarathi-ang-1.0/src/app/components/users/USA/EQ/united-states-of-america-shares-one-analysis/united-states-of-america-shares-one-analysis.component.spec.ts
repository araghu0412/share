import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UnitedStatesOfAmericaSharesOneAnalysisComponent } from './united-states-of-america-shares-one-analysis.component';

describe('UnitedStatesOfAmericaSharesOneAnalysisComponent', () => {
  let component: UnitedStatesOfAmericaSharesOneAnalysisComponent;
  let fixture: ComponentFixture<UnitedStatesOfAmericaSharesOneAnalysisComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UnitedStatesOfAmericaSharesOneAnalysisComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UnitedStatesOfAmericaSharesOneAnalysisComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
