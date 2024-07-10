import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BhaarathaHomeComponent } from './bhaaratha-home.component';

describe('BhaarathaHomeComponent', () => {
  let component: BhaarathaHomeComponent;
  let fixture: ComponentFixture<BhaarathaHomeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BhaarathaHomeComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(BhaarathaHomeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
