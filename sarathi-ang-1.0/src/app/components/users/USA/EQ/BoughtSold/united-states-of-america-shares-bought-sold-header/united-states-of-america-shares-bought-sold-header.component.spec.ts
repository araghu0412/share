import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UnitedStatesOfAmericaSharesBoughtSoldHeaderComponent } from './united-states-of-america-shares-bought-sold-header.component';

describe('UnitedStatesOfAmericaSharesBoughtSoldHeaderComponent', () => {
  let component: UnitedStatesOfAmericaSharesBoughtSoldHeaderComponent;
  let fixture: ComponentFixture<UnitedStatesOfAmericaSharesBoughtSoldHeaderComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UnitedStatesOfAmericaSharesBoughtSoldHeaderComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UnitedStatesOfAmericaSharesBoughtSoldHeaderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
