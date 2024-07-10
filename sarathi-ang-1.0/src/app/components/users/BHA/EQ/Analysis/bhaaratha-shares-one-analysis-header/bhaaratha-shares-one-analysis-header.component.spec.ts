import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BhaarathaSharesOneAnalysisHeaderComponent } from './bhaaratha-shares-one-analysis-header.component';

describe('BhaarathaSharesOneAnalysisHeaderComponent', () => {
  let component: BhaarathaSharesOneAnalysisHeaderComponent;
  let fixture: ComponentFixture<BhaarathaSharesOneAnalysisHeaderComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BhaarathaSharesOneAnalysisHeaderComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BhaarathaSharesOneAnalysisHeaderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
