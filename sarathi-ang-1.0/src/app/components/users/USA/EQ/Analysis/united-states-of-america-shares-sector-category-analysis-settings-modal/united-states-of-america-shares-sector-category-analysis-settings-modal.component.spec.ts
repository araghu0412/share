import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UnitedStatesOfAmericaSharesSectorCategoryAnalysisSettingsModalComponent } from './united-states-of-america-shares-sector-category-analysis-settings-modal.component';

describe('UnitedStatesOfAmericaSharesSectorCategoryAnalysisSettingsModalComponent', () => {
  let component: UnitedStatesOfAmericaSharesSectorCategoryAnalysisSettingsModalComponent;
  let fixture: ComponentFixture<UnitedStatesOfAmericaSharesSectorCategoryAnalysisSettingsModalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UnitedStatesOfAmericaSharesSectorCategoryAnalysisSettingsModalComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UnitedStatesOfAmericaSharesSectorCategoryAnalysisSettingsModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
