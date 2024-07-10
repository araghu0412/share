import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BhaarathaSharesBoughtComponent } from './bhaaratha-shares-bought.component';

describe('BhaarathaSharesBoughtComponent', () => {
  let component: BhaarathaSharesBoughtComponent;
  let fixture: ComponentFixture<BhaarathaSharesBoughtComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BhaarathaSharesBoughtComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(BhaarathaSharesBoughtComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
