import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UnitedStatesOfAmericaSharesHomeHeaderComponent } from './united-states-of-america-shares-home-header.component';

describe('UnitedStatesOfAmericaSharesHomeHeaderComponent', () => {
  let component: UnitedStatesOfAmericaSharesHomeHeaderComponent;
  let fixture: ComponentFixture<UnitedStatesOfAmericaSharesHomeHeaderComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UnitedStatesOfAmericaSharesHomeHeaderComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UnitedStatesOfAmericaSharesHomeHeaderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
