import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BhaarathaSharesSectorCategoryAnalysisHeaderComponent } from './bhaaratha-shares-sector-category-analysis-header.component';

describe('BhaarathaSharesSectorCategoryAnalysisHeaderComponent', () => {
  let component: BhaarathaSharesSectorCategoryAnalysisHeaderComponent;
  let fixture: ComponentFixture<BhaarathaSharesSectorCategoryAnalysisHeaderComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BhaarathaSharesSectorCategoryAnalysisHeaderComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BhaarathaSharesSectorCategoryAnalysisHeaderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
