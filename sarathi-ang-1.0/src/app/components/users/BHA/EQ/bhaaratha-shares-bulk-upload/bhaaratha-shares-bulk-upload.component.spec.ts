import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BhaarathaSharesBulkUploadComponent } from './bhaaratha-shares-bulk-upload.component';

describe('BhaarathaSharesBulkUploadComponent', () => {
  let component: BhaarathaSharesBulkUploadComponent;
  let fixture: ComponentFixture<BhaarathaSharesBulkUploadComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BhaarathaSharesBulkUploadComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BhaarathaSharesBulkUploadComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
