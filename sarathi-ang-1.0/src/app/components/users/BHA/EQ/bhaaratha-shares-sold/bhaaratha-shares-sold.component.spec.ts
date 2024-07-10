import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BhaarathaSharesSoldComponent } from './bhaaratha-shares-sold.component';

describe('BhaarathaSharesSoldComponent', () => {
  let component: BhaarathaSharesSoldComponent;
  let fixture: ComponentFixture<BhaarathaSharesSoldComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BhaarathaSharesSoldComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(BhaarathaSharesSoldComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
