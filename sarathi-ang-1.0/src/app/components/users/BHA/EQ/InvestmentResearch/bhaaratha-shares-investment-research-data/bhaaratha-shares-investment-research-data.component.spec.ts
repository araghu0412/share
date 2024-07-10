import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BhaarathaSharesInvestmentResearchDataComponent } from './bhaaratha-shares-investment-research-data.component';

describe('BhaarathaSharesInvestmentResearchDataComponent', () => {
  let component: BhaarathaSharesInvestmentResearchDataComponent;
  let fixture: ComponentFixture<BhaarathaSharesInvestmentResearchDataComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BhaarathaSharesInvestmentResearchDataComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BhaarathaSharesInvestmentResearchDataComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
