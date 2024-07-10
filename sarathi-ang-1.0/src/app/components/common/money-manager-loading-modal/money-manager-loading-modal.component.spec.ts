import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MoneyManagerLoadingModalComponent } from './money-manager-loading-modal.component';

describe('MoneyManagerLoadingModalComponent', () => {
  let component: MoneyManagerLoadingModalComponent;
  let fixture: ComponentFixture<MoneyManagerLoadingModalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MoneyManagerLoadingModalComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MoneyManagerLoadingModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
