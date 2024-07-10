import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BhaarathaSharesShortTermInvestmentDataComponent } from './bhaaratha-shares-short-term-investment-data.component';

describe('BhaarathaSharesShortTermInvestmentDataComponent', () => {
  let component: BhaarathaSharesShortTermInvestmentDataComponent;
  let fixture: ComponentFixture<BhaarathaSharesShortTermInvestmentDataComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BhaarathaSharesShortTermInvestmentDataComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BhaarathaSharesShortTermInvestmentDataComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
