import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SearchApartmentsComponent } from './search-apartments.component';

describe('SearchApartmentsComponent', () => {
  let component: SearchApartmentsComponent;
  let fixture: ComponentFixture<SearchApartmentsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SearchApartmentsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SearchApartmentsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
