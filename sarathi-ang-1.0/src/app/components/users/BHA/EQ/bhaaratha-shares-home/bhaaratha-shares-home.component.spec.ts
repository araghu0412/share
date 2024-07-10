import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BhaarathaSharesHomeComponent } from './bhaaratha-shares-home.component';

describe('BhaarathaSharesHomeComponent', () => {
  let component: BhaarathaSharesHomeComponent;
  let fixture: ComponentFixture<BhaarathaSharesHomeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BhaarathaSharesHomeComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(BhaarathaSharesHomeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
