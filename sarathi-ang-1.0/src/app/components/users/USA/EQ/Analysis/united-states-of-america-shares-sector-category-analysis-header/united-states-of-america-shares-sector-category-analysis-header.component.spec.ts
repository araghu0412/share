import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UnitedStatesOfAmericaSharesSectorCategoryAnalysisHeaderComponent } from './united-states-of-america-shares-sector-category-analysis-header.component';

describe('UnitedStatesOfAmericaSharesSectorCategoryAnalysisHeaderComponent', () => {
  let component: UnitedStatesOfAmericaSharesSectorCategoryAnalysisHeaderComponent;
  let fixture: ComponentFixture<UnitedStatesOfAmericaSharesSectorCategoryAnalysisHeaderComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UnitedStatesOfAmericaSharesSectorCategoryAnalysisHeaderComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UnitedStatesOfAmericaSharesSectorCategoryAnalysisHeaderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
