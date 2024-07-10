import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MoneyManagerLoadingComponent } from './money-manager-loading.component';

describe('LoadingComponent', () => {
  let component: MoneyManagerLoadingComponent;
  let fixture: ComponentFixture<MoneyManagerLoadingComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MoneyManagerLoadingComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MoneyManagerLoadingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
