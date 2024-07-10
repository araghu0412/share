import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BhaarathaSharesAnalysisServicesHeaderComponent } from './bhaaratha-shares-analysis-services-header.component';

describe('BhaarathaSharesAnalysisServicesHeaderComponent', () => {
  let component: BhaarathaSharesAnalysisServicesHeaderComponent;
  let fixture: ComponentFixture<BhaarathaSharesAnalysisServicesHeaderComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BhaarathaSharesAnalysisServicesHeaderComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BhaarathaSharesAnalysisServicesHeaderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
