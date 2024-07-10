import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UnitedStatesOfAmericaSharesSoldComponent } from './united-states-of-america-shares-sold.component';

describe('UnitedStatesOfAmericaSharesSoldComponent', () => {
  let component: UnitedStatesOfAmericaSharesSoldComponent;
  let fixture: ComponentFixture<UnitedStatesOfAmericaSharesSoldComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UnitedStatesOfAmericaSharesSoldComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UnitedStatesOfAmericaSharesSoldComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
