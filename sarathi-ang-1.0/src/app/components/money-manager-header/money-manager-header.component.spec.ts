import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MoneyManagerHeaderComponent } from './money-manager-header.component';

describe('MoneyManagerHeaderComponent', () => {
  let component: MoneyManagerHeaderComponent;
  let fixture: ComponentFixture<MoneyManagerHeaderComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MoneyManagerHeaderComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MoneyManagerHeaderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
