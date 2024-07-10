import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BhaarathaSharesSectorCategoryAnalysisDataComponent } from './bhaaratha-shares-sector-category-analysis-data.component';

describe('BhaarathaSharesSectorCategoryAnalysisDataComponent', () => {
  let component: BhaarathaSharesSectorCategoryAnalysisDataComponent;
  let fixture: ComponentFixture<BhaarathaSharesSectorCategoryAnalysisDataComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BhaarathaSharesSectorCategoryAnalysisDataComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BhaarathaSharesSectorCategoryAnalysisDataComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
