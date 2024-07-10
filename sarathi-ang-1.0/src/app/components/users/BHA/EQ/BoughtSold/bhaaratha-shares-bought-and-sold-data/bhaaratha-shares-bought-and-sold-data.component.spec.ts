import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BhaarathaSharesBoughtAndSoldDataComponent } from './bhaaratha-shares-bought-and-sold-data.component';

describe('BhaarathaSharesBoughtAndSoldDataComponent', () => {
  let component: BhaarathaSharesBoughtAndSoldDataComponent;
  let fixture: ComponentFixture<BhaarathaSharesBoughtAndSoldDataComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BhaarathaSharesBoughtAndSoldDataComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BhaarathaSharesBoughtAndSoldDataComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
