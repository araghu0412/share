import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UnitedStatesOfAmericaSharesBoughtComponent } from './united-states-of-america-shares-bought.component';

describe('UnitedStatesOfAmericaSharesBoughtComponent', () => {
  let component: UnitedStatesOfAmericaSharesBoughtComponent;
  let fixture: ComponentFixture<UnitedStatesOfAmericaSharesBoughtComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UnitedStatesOfAmericaSharesBoughtComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UnitedStatesOfAmericaSharesBoughtComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
