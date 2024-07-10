import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UnitedStatesOfAmericaSharesAddSharesHeaderComponent } from './united-states-of-america-shares-add-shares-header.component';

describe('UnitedStatesOfAmericaSharesAddSharesHeaderComponent', () => {
  let component: UnitedStatesOfAmericaSharesAddSharesHeaderComponent;
  let fixture: ComponentFixture<UnitedStatesOfAmericaSharesAddSharesHeaderComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UnitedStatesOfAmericaSharesAddSharesHeaderComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UnitedStatesOfAmericaSharesAddSharesHeaderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
