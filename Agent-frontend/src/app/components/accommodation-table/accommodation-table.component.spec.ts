import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { AccommodationTableComponent } from './accommodation-table.component';

describe('AccommodationTableComponent', () => {
  let component: AccommodationTableComponent;
  let fixture: ComponentFixture<AccommodationTableComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AccommodationTableComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AccommodationTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
