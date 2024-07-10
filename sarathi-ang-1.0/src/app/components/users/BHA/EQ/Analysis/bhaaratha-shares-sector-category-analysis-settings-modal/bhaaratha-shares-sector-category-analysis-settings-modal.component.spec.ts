import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BhaarathaSharesSectorCategoryAnalysisSettingsModalComponent } from './bhaaratha-shares-sector-category-analysis-settings-modal.component';

describe('BhaarathaSharesSectorCategoryAnalysisSettingsModalComponent', () => {
  let component: BhaarathaSharesSectorCategoryAnalysisSettingsModalComponent;
  let fixture: ComponentFixture<BhaarathaSharesSectorCategoryAnalysisSettingsModalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BhaarathaSharesSectorCategoryAnalysisSettingsModalComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BhaarathaSharesSectorCategoryAnalysisSettingsModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
