import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BhaarathaSharesAddSoldComponent } from './bhaaratha-shares-add-sold.component';

describe('BhaarathaSharesAddSoldComponent', () => {
  let component: BhaarathaSharesAddSoldComponent;
  let fixture: ComponentFixture<BhaarathaSharesAddSoldComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BhaarathaSharesAddSoldComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BhaarathaSharesAddSoldComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
