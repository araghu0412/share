import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BhaarathaSharesAnalysisComponent } from './bhaaratha-shares-analysis.component';

describe('BhaarathaSharesAnalysisComponent', () => {
  let component: BhaarathaSharesAnalysisComponent;
  let fixture: ComponentFixture<BhaarathaSharesAnalysisComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BhaarathaSharesAnalysisComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(BhaarathaSharesAnalysisComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
