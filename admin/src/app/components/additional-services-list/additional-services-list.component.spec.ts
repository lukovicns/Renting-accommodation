import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AdditionalServicesListComponent } from './additional-services-list.component';

describe('AdditionalServicesListComponent', () => {
  let component: AdditionalServicesListComponent;
  let fixture: ComponentFixture<AdditionalServicesListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AdditionalServicesListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AdditionalServicesListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
