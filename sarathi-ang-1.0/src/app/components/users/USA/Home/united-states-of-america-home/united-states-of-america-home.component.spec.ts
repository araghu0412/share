import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UnitedStatesOfAmericaHomeComponent } from './united-states-of-america-home.component';

describe('UnitedStatesOfAmericaHomeComponent', () => {
  let component: UnitedStatesOfAmericaHomeComponent;
  let fixture: ComponentFixture<UnitedStatesOfAmericaHomeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UnitedStatesOfAmericaHomeComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UnitedStatesOfAmericaHomeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
