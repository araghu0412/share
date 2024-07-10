import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BhaarathaSharesOneShareDetailsHeaderComponent } from './bhaaratha-shares-one-share-details-header.component';

describe('BhaarathaSharesOneShareDetailsHeaderComponent', () => {
  let component: BhaarathaSharesOneShareDetailsHeaderComponent;
  let fixture: ComponentFixture<BhaarathaSharesOneShareDetailsHeaderComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BhaarathaSharesOneShareDetailsHeaderComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BhaarathaSharesOneShareDetailsHeaderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
