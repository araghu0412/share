import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BhaarathaSharesAddBoughtComponent } from './bhaaratha-shares-add-bought.component';

describe('BhaarathaSharesAddBoughtComponent', () => {
  let component: BhaarathaSharesAddBoughtComponent;
  let fixture: ComponentFixture<BhaarathaSharesAddBoughtComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BhaarathaSharesAddBoughtComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BhaarathaSharesAddBoughtComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
