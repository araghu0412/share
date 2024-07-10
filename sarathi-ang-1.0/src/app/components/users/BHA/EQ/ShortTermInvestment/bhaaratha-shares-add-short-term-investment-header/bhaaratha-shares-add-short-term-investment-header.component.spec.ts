import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BhaarathaSharesAddShortTermInvestmentHeaderComponent } from './bhaaratha-shares-add-short-term-investment-header.component';

describe('BhaarathaSharesAddShortTermInvestmentHeaderComponent', () => {
  let component: BhaarathaSharesAddShortTermInvestmentHeaderComponent;
  let fixture: ComponentFixture<BhaarathaSharesAddShortTermInvestmentHeaderComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BhaarathaSharesAddShortTermInvestmentHeaderComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BhaarathaSharesAddShortTermInvestmentHeaderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
