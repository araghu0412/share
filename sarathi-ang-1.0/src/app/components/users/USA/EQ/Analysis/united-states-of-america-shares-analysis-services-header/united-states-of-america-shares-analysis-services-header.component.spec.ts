import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UnitedStatesOfAmericaSharesAnalysisServicesHeaderComponent } from './united-states-of-america-shares-analysis-services-header.component';

describe('UnitedStatesOfAmericaSharesAnalysisServicesHeaderComponent', () => {
  let component: UnitedStatesOfAmericaSharesAnalysisServicesHeaderComponent;
  let fixture: ComponentFixture<UnitedStatesOfAmericaSharesAnalysisServicesHeaderComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UnitedStatesOfAmericaSharesAnalysisServicesHeaderComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UnitedStatesOfAmericaSharesAnalysisServicesHeaderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
