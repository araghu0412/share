import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BhaarathaSharesShortTermInvestmentHeaderComponent } from './bhaaratha-shares-short-term-investment-header.component';

describe('BhaarathaSharesShortTermInvestmentHeaderComponent', () => {
  let component: BhaarathaSharesShortTermInvestmentHeaderComponent;
  let fixture: ComponentFixture<BhaarathaSharesShortTermInvestmentHeaderComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BhaarathaSharesShortTermInvestmentHeaderComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BhaarathaSharesShortTermInvestmentHeaderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
