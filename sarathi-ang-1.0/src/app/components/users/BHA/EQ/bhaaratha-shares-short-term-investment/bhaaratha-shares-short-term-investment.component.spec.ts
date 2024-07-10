import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BhaarathaSharesShortTermInvestmentComponent } from './bhaaratha-shares-short-term-investment.component';

describe('BhaarathaSharesShortTermInvestmentComponent', () => {
  let component: BhaarathaSharesShortTermInvestmentComponent;
  let fixture: ComponentFixture<BhaarathaSharesShortTermInvestmentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BhaarathaSharesShortTermInvestmentComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BhaarathaSharesShortTermInvestmentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
