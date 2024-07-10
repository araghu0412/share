import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UnitedStatesOfAmericaSharesSectorCategoryAnalysisDataComponent } from './united-states-of-america-shares-sector-category-analysis-data.component';

describe('UnitedStatesOfAmericaSharesSectorCategoryAnalysisDataComponent', () => {
  let component: UnitedStatesOfAmericaSharesSectorCategoryAnalysisDataComponent;
  let fixture: ComponentFixture<UnitedStatesOfAmericaSharesSectorCategoryAnalysisDataComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UnitedStatesOfAmericaSharesSectorCategoryAnalysisDataComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UnitedStatesOfAmericaSharesSectorCategoryAnalysisDataComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
