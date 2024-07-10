import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BhaarathaMutualFundsHomeComponent } from './bhaaratha-mutual-funds-home.component';

describe('BhaarathaMutualFundsHomeComponent', () => {
  let component: BhaarathaMutualFundsHomeComponent;
  let fixture: ComponentFixture<BhaarathaMutualFundsHomeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BhaarathaMutualFundsHomeComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(BhaarathaMutualFundsHomeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
