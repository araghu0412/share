import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BhaarathaSharesBoughtSettingsModalComponent } from './bhaaratha-shares-bought-settings-modal.component';

describe('BhaarathaSharesBoughtSettingsModalComponent', () => {
  let component: BhaarathaSharesBoughtSettingsModalComponent;
  let fixture: ComponentFixture<BhaarathaSharesBoughtSettingsModalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BhaarathaSharesBoughtSettingsModalComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BhaarathaSharesBoughtSettingsModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
