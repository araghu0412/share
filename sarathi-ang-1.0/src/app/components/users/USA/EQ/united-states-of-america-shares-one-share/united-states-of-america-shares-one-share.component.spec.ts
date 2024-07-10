import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UnitedStatesOfAmericaSharesOneShareComponent } from './united-states-of-america-shares-one-share.component';

describe('UnitedStatesOfAmericaSharesOneShareComponent', () => {
  let component: UnitedStatesOfAmericaSharesOneShareComponent;
  let fixture: ComponentFixture<UnitedStatesOfAmericaSharesOneShareComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UnitedStatesOfAmericaSharesOneShareComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UnitedStatesOfAmericaSharesOneShareComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
