import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BhaarathaMutualFundsBoughtComponent } from './bhaaratha-mutual-funds-bought.component';

describe('BhaarathaMutualFundsBoughtComponent', () => {
  let component: BhaarathaMutualFundsBoughtComponent;
  let fixture: ComponentFixture<BhaarathaMutualFundsBoughtComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BhaarathaMutualFundsBoughtComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(BhaarathaMutualFundsBoughtComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
