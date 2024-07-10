import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BhaarathaSharesCompleteAnalysisComponent } from './bhaaratha-shares-complete-analysis.component';

describe('BhaarathaSharesCompleteAnalysisComponent', () => {
  let component: BhaarathaSharesCompleteAnalysisComponent;
  let fixture: ComponentFixture<BhaarathaSharesCompleteAnalysisComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BhaarathaSharesCompleteAnalysisComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BhaarathaSharesCompleteAnalysisComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
