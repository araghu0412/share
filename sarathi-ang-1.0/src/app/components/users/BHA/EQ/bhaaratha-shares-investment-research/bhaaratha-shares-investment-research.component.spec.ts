import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BhaarathaSharesInvestmentResearchComponent } from './bhaaratha-shares-investment-research.component';

describe('BhaarathaSharesInvestmentResearchComponent', () => {
  let component: BhaarathaSharesInvestmentResearchComponent;
  let fixture: ComponentFixture<BhaarathaSharesInvestmentResearchComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BhaarathaSharesInvestmentResearchComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BhaarathaSharesInvestmentResearchComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
