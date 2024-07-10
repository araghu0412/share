import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UnitedStatesOfAmericaSharesAddSoldComponent } from './united-states-of-america-shares-add-sold.component';

describe('UnitedStatesOfAmericaSharesAddSoldComponent', () => {
  let component: UnitedStatesOfAmericaSharesAddSoldComponent;
  let fixture: ComponentFixture<UnitedStatesOfAmericaSharesAddSoldComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UnitedStatesOfAmericaSharesAddSoldComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UnitedStatesOfAmericaSharesAddSoldComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
