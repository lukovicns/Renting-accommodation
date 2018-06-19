import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ApproveAgentsComponent } from './approve-agents.component';

describe('ApproveAgentsComponent', () => {
  let component: ApproveAgentsComponent;
  let fixture: ComponentFixture<ApproveAgentsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ApproveAgentsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ApproveAgentsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
