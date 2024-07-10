import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BhaarathaSharesOneAnalysisComponent } from './bhaaratha-shares-one-analysis.component';

describe('BhaarathaSharesOneAnalysisComponent', () => {
  let component: BhaarathaSharesOneAnalysisComponent;
  let fixture: ComponentFixture<BhaarathaSharesOneAnalysisComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BhaarathaSharesOneAnalysisComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BhaarathaSharesOneAnalysisComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
