import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UnitedStatesOfAmericaComponent } from './united-states-of-america.component';

describe('UnitedStatesOfAmericaComponent', () => {
  let component: UnitedStatesOfAmericaComponent;
  let fixture: ComponentFixture<UnitedStatesOfAmericaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UnitedStatesOfAmericaComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UnitedStatesOfAmericaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
