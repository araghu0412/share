import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MoneyManagerCheckBoxComponent } from './money-manager-check-box.component';

describe('MoneyManagerCheckBoxComponent', () => {
  let component: MoneyManagerCheckBoxComponent;
  let fixture: ComponentFixture<MoneyManagerCheckBoxComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MoneyManagerCheckBoxComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MoneyManagerCheckBoxComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
