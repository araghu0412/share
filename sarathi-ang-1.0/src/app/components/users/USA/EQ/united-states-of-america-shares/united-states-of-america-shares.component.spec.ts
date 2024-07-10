import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UnitedStatesOfAmericaSharesComponent } from './united-states-of-america-shares.component';

describe('UnitedStatesOfAmericaSharesComponent', () => {
  let component: UnitedStatesOfAmericaSharesComponent;
  let fixture: ComponentFixture<UnitedStatesOfAmericaSharesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UnitedStatesOfAmericaSharesComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UnitedStatesOfAmericaSharesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
