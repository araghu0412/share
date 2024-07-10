import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BhaarathaMutualFundsAnalysisComponent } from './bhaaratha-mutual-funds-analysis.component';

describe('BhaarathaMutualFundsAnalysisComponent', () => {
  let component: BhaarathaMutualFundsAnalysisComponent;
  let fixture: ComponentFixture<BhaarathaMutualFundsAnalysisComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BhaarathaMutualFundsAnalysisComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(BhaarathaMutualFundsAnalysisComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
