import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BhaarathaMutualFundsSoldComponent } from './bhaaratha-mutual-funds-sold.component';

describe('BhaarathaMutualFundsSoldComponent', () => {
  let component: BhaarathaMutualFundsSoldComponent;
  let fixture: ComponentFixture<BhaarathaMutualFundsSoldComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BhaarathaMutualFundsSoldComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(BhaarathaMutualFundsSoldComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
