import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BhaarathaSharesHomeHeaderComponent } from './bhaaratha-shares-home-header.component';

describe('BhaarathaSharesHomeHeaderComponent', () => {
  let component: BhaarathaSharesHomeHeaderComponent;
  let fixture: ComponentFixture<BhaarathaSharesHomeHeaderComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BhaarathaSharesHomeHeaderComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BhaarathaSharesHomeHeaderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
