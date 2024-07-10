import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BhaarathaSharesDividendYieldAnalysisComponent } from './bhaaratha-shares-dividend-yield-analysis.component';

describe('BhaarathaSharesDividendYieldAnalysisComponent', () => {
  let component: BhaarathaSharesDividendYieldAnalysisComponent;
  let fixture: ComponentFixture<BhaarathaSharesDividendYieldAnalysisComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BhaarathaSharesDividendYieldAnalysisComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BhaarathaSharesDividendYieldAnalysisComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
