import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BhaarathaSharesOneShareShortTermInvestmentComponent } from './bhaaratha-shares-one-share-short-term-investment.component';

describe('BhaarathaSharesOneShareShortTermInvestmentComponent', () => {
  let component: BhaarathaSharesOneShareShortTermInvestmentComponent;
  let fixture: ComponentFixture<BhaarathaSharesOneShareShortTermInvestmentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BhaarathaSharesOneShareShortTermInvestmentComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BhaarathaSharesOneShareShortTermInvestmentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
