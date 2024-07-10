import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UnitedStatesOfAmericaSharesHomeComponent } from './united-states-of-america-shares-home.component';

describe('UnitedStatesOfAmericaSharesHomeComponent', () => {
  let component: UnitedStatesOfAmericaSharesHomeComponent;
  let fixture: ComponentFixture<UnitedStatesOfAmericaSharesHomeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UnitedStatesOfAmericaSharesHomeComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UnitedStatesOfAmericaSharesHomeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
