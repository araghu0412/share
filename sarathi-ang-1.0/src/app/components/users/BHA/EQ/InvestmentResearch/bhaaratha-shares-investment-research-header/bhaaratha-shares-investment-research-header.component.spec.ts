import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BhaarathaSharesInvestmentResearchHeaderComponent } from './bhaaratha-shares-investment-research-header.component';

describe('BhaarathaSharesInvestmentResearchHeaderComponent', () => {
  let component: BhaarathaSharesInvestmentResearchHeaderComponent;
  let fixture: ComponentFixture<BhaarathaSharesInvestmentResearchHeaderComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BhaarathaSharesInvestmentResearchHeaderComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BhaarathaSharesInvestmentResearchHeaderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
