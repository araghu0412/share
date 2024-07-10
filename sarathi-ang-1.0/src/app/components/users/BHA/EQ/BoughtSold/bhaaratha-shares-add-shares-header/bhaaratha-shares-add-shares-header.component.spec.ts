import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BhaarathaSharesAddSharesHeaderComponent } from './bhaaratha-shares-add-shares-header.component';

describe('BhaarathaSharesAddSharesHeaderComponent', () => {
  let component: BhaarathaSharesAddSharesHeaderComponent;
  let fixture: ComponentFixture<BhaarathaSharesAddSharesHeaderComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BhaarathaSharesAddSharesHeaderComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BhaarathaSharesAddSharesHeaderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
