import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BhaarathaMutualFundsComponent } from './bhaaratha-mutual-funds.component';

describe('BhaarathaMutualFundsComponent', () => {
  let component: BhaarathaMutualFundsComponent;
  let fixture: ComponentFixture<BhaarathaMutualFundsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BhaarathaMutualFundsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(BhaarathaMutualFundsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
