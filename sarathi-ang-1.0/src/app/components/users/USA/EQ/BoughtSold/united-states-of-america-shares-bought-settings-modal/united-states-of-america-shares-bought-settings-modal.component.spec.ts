import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UnitedStatesOfAmericaSharesBoughtSettingsModalComponent } from './united-states-of-america-shares-bought-settings-modal.component';

describe('UnitedStatesOfAmericaSharesBoughtSettingsModalComponent', () => {
  let component: UnitedStatesOfAmericaSharesBoughtSettingsModalComponent;
  let fixture: ComponentFixture<UnitedStatesOfAmericaSharesBoughtSettingsModalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UnitedStatesOfAmericaSharesBoughtSettingsModalComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UnitedStatesOfAmericaSharesBoughtSettingsModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
