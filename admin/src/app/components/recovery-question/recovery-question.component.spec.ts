import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RecoveryQuestionComponent } from './recovery-question.component';

describe('RecoveryQuestionComponent', () => {
  let component: RecoveryQuestionComponent;
  let fixture: ComponentFixture<RecoveryQuestionComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RecoveryQuestionComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RecoveryQuestionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
