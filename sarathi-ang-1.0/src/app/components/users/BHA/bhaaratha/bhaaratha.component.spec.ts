import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BhaarathaComponent } from './bhaaratha.component';

describe('BhaarathaComponent', () => {
  let component: BhaarathaComponent;
  let fixture: ComponentFixture<BhaarathaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BhaarathaComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(BhaarathaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
