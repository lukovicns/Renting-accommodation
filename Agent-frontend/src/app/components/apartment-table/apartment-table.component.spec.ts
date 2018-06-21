import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { ApartmentTableComponent } from './apartment-table.component';

describe('AparmentTableComponent', () => {
  let component: ApartmentTableComponent;
  let fixture: ComponentFixture<ApartmentTableComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ApartmentTableComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ApartmentTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
