import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BhaarathaSharesComponent } from './bhaaratha-shares.component';

describe('BhaarathaSharesComponent', () => {
  let component: BhaarathaSharesComponent;
  let fixture: ComponentFixture<BhaarathaSharesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BhaarathaSharesComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(BhaarathaSharesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
