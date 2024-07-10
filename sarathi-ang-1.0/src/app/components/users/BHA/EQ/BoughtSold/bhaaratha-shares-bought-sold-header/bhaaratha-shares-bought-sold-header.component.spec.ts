import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BhaarathaSharesBoughtSoldHeaderComponent } from './bhaaratha-shares-bought-sold-header.component';

describe('BhaarathaSharesBoughtSoldHeaderComponent', () => {
  let component: BhaarathaSharesBoughtSoldHeaderComponent;
  let fixture: ComponentFixture<BhaarathaSharesBoughtSoldHeaderComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BhaarathaSharesBoughtSoldHeaderComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BhaarathaSharesBoughtSoldHeaderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
