import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UnitedStatesOfAmericaSharesBoughtAndSoldDataComponent } from './united-states-of-america-shares-bought-and-sold-data.component';

describe('UnitedStatesOfAmericaSharesBoughtAndSoldDataComponent', () => {
  let component: UnitedStatesOfAmericaSharesBoughtAndSoldDataComponent;
  let fixture: ComponentFixture<UnitedStatesOfAmericaSharesBoughtAndSoldDataComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UnitedStatesOfAmericaSharesBoughtAndSoldDataComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UnitedStatesOfAmericaSharesBoughtAndSoldDataComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
