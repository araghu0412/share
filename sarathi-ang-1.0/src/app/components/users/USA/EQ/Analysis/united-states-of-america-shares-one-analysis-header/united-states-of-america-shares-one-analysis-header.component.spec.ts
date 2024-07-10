import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UnitedStatesOfAmericaSharesOneAnalysisHeaderComponent } from './united-states-of-america-shares-one-analysis-header.component';

describe('UnitedStatesOfAmericaSharesOneAnalysisHeaderComponent', () => {
  let component: UnitedStatesOfAmericaSharesOneAnalysisHeaderComponent;
  let fixture: ComponentFixture<UnitedStatesOfAmericaSharesOneAnalysisHeaderComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UnitedStatesOfAmericaSharesOneAnalysisHeaderComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UnitedStatesOfAmericaSharesOneAnalysisHeaderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
