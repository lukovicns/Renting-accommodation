import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SearchAccommodationComponent } from './search-accommodation.component';

describe('SearchAccommodationComponent', () => {
  let component: SearchAccommodationComponent;
  let fixture: ComponentFixture<SearchAccommodationComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SearchAccommodationComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SearchAccommodationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
