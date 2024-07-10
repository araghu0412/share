import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BhaarathaSharesOneShareComponent } from './bhaaratha-shares-one-share.component';

describe('BhaarathaSharesOneShareComponent', () => {
  let component: BhaarathaSharesOneShareComponent;
  let fixture: ComponentFixture<BhaarathaSharesOneShareComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BhaarathaSharesOneShareComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BhaarathaSharesOneShareComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
