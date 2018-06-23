import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EditAdditionalServiceComponent } from './edit-additional-service.component';

describe('EditAdditionalServiceComponent', () => {
  let component: EditAdditionalServiceComponent;
  let fixture: ComponentFixture<EditAdditionalServiceComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EditAdditionalServiceComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EditAdditionalServiceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
