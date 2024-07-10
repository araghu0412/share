import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BhaarathaSharesAddShortTermInvestmentComponent } from './bhaaratha-shares-add-short-term-investment.component';

describe('BhaarathaSharesAddShortTermInvestmentComponent', () => {
  let component: BhaarathaSharesAddShortTermInvestmentComponent;
  let fixture: ComponentFixture<BhaarathaSharesAddShortTermInvestmentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BhaarathaSharesAddShortTermInvestmentComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BhaarathaSharesAddShortTermInvestmentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
