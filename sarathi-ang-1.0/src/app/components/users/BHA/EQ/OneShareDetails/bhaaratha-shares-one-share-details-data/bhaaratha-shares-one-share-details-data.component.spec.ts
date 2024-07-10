import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BhaarathaSharesOneShareDetailsDataComponent } from './bhaaratha-shares-one-share-details-data.component';

describe('BhaarathaSharesOneShareDetailsDataComponent', () => {
  let component: BhaarathaSharesOneShareDetailsDataComponent;
  let fixture: ComponentFixture<BhaarathaSharesOneShareDetailsDataComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BhaarathaSharesOneShareDetailsDataComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BhaarathaSharesOneShareDetailsDataComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
