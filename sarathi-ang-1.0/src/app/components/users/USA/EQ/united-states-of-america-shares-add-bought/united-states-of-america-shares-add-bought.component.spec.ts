import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UnitedStatesOfAmericaSharesAddBoughtComponent } from './united-states-of-america-shares-add-bought.component';

describe('UnitedStatesOfAmericaSharesAddBoughtComponent', () => {
  let component: UnitedStatesOfAmericaSharesAddBoughtComponent;
  let fixture: ComponentFixture<UnitedStatesOfAmericaSharesAddBoughtComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UnitedStatesOfAmericaSharesAddBoughtComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UnitedStatesOfAmericaSharesAddBoughtComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
