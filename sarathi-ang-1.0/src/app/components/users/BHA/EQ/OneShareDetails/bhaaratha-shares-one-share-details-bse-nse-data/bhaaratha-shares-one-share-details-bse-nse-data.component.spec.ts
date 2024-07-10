import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BhaarathaSharesOneShareDetailsBseNseDataComponent } from './bhaaratha-shares-one-share-details-bse-nse-data.component';

describe('BhaarathaSharesOneShareDetailsBseNseDataComponent', () => {
  let component: BhaarathaSharesOneShareDetailsBseNseDataComponent;
  let fixture: ComponentFixture<BhaarathaSharesOneShareDetailsBseNseDataComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BhaarathaSharesOneShareDetailsBseNseDataComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BhaarathaSharesOneShareDetailsBseNseDataComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
